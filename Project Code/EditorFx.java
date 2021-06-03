package schoolink;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import javafx.stage.FileChooser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;

public class EditorFx extends Application {
	
	public static EditorFx instance;
	
	static String html_code;
	
	private static int my_width;
	private static int my_height;
	private static String my_title_txt;
	private static String my_init_html;
	private static HTMLEditor ed;
	private static Scene parent_scene;
	private static Stage primStage;
	private static Stage main_dialog;
	private static int file_no; 
	private static String file_replace_str1 = "_%$_##";
	private static String file_replace_str2 = "%$_##_";
	private static ArrayList<String> files;
	private static ArrayList<String> images;
	
	
    public void start(Stage primaryStage) throws Exception {
    	primStage = primaryStage;
    }
	
	public EditorFx(int width, int height, String title_txt, String init_html_content) {
		instance = this;
    	file_no = 1;
    	files = new ArrayList<String>();
    	images = new ArrayList<String>();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	my_width = width;
            	my_height = height;
            	my_title_txt = title_txt;
            	my_init_html = init_html_content;
                initAndShowGUI();
            }
        });		
	}

    private static void initAndShowGUI() {
        final JFXPanel fxPanel = new JFXPanel();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
       });
    }

    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
    	parent_scene = createScene();
        fxPanel.setScene(parent_scene);
    }

    private static Scene createScene() {
    	Stage dialog = new Stage();
    	main_dialog = dialog;
    	/*try {
    		URL.setURLStreamHandlerFactory(protocol -> {
    			if (protocol.startsWith("http")) {
    				return new CustomUrlHandler();
    			}
    			return null;
    		});
    	} catch (Error e) {
    	    //e.printStackTrace();
    		System.err.println("Url handlig already installed...");
    	}*/
        
        VBox root = new VBox();      
        root.setPadding(new Insets(8, 8, 8, 8));
        root.setSpacing(5);
        root.setAlignment(Pos.BOTTOM_LEFT);
        //Group  root  =  new  Group();
        Scene  scene  =  new  Scene(root, Color.ALICEBLUE);

        Text  text  =  new  Text();
        
        text.setX(40);
        text.setY(100);
        text.setFont(new Font(25));
        text.setText(my_title_txt);
        
        root.getChildren().add(text);
        ed = new HTMLEditor();
        ed.setPrefWidth(my_width);
        ed.setPrefHeight(my_height-100);
		ed.setHtmlText(my_init_html);
		ed.setStyle(
			    "-fx-font: 12 cambria;"
			    + "-fx-border-color: brown; "
			    //+ "-fx-border-style: dotted;"
			    //+ "-fx-border-width: 2;"
			);
		
		Node node = ed.lookup(".top-toolbar");
	    if (node instanceof ToolBar) {
	      ToolBar bar = (ToolBar) node;
	      ImageView graphic = new ImageView(new Image(EditorFx.class.getResource("/images/ed_table.png").toExternalForm(), 16, 16, true, true));
	      Button tblButton = new Button("", graphic);
	      ImageView graphic1 = new ImageView(new Image(EditorFx.class.getResource("/images/ed_image.png").toExternalForm(), 16, 16, true, true));
	      Button imgButton = new Button("", graphic1);
	      ImageView graphic2 = new ImageView(new Image(EditorFx.class.getResource("/images/ed_link.png").toExternalForm(), 16, 16, true, true));     
	      Button lnkButton = new Button("", graphic2);
	      ImageView graphic3 = new ImageView(new Image(EditorFx.class.getResource("/images/ed_import.png").toExternalForm(), 16, 16, true, true));     
	      Button pdfButton = new Button("", graphic3);
	      bar.getItems().addAll(tblButton, imgButton,lnkButton,pdfButton, new Separator());
          tblButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent arg0) {
	            Dialog<Pair<Integer, Integer>> ndialog = new Dialog<>();
	            ndialog.setTitle("Give rows and columns");
	            ndialog.initOwner(main_dialog);
	            ndialog.initModality(Modality.WINDOW_MODAL);

	            ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
	            ndialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

	            GridPane gridPane = new GridPane();
	            gridPane.setHgap(10);
	            gridPane.setVgap(10);
	            gridPane.setPadding(new Insets(20, 150, 10, 10));
           
	            Spinner<Integer> rows = new Spinner<Integer>(2, 30, 2);
	            rows.setEditable(true);
	            rows.setPrefSize(75, 25);
	            
	            Spinner<Integer> cols = new Spinner<Integer>(2, 10, 2);
	            cols.setEditable(true);
	            cols.setPrefSize(75, 25);           

	            gridPane.add(new Label("Rows:"), 0, 0);
	            gridPane.add(rows, 1, 0);
	            gridPane.add(new Label("Columns:"), 2, 0);
	            gridPane.add(cols, 3, 0);

	            ndialog.getDialogPane().setContent(gridPane);

	            // Request focus on the username field by default.
	            Platform.runLater(() -> rows.requestFocus());

	            ndialog.setResultConverter(dialogButton -> {
	                if (dialogButton == loginButtonType) {
	                    return new Pair<Integer, Integer>(rows.getValue(), cols.getValue());
	                }
	                return null;
	            });

	            Optional<Pair<Integer, Integer>> result = ndialog.showAndWait();

	            result.ifPresent(pair -> {
	                //System.out.println("From=" + pair.getKey() + ", To=" + pair.getValue() + "prepare html table");
	            	int tbl_rows = pair.getKey();
	            	int tbl_cols = pair.getValue();
	            	String row_txt = "";
	            	int k;
	            	int width_per_column = 100/tbl_cols;
	            	for (k=0;k<tbl_cols;k++) row_txt += "<td style= height:25px;\"width:" + width_per_column + "%\"></td>";
	            	row_txt = "<tr>" + row_txt + "</tr>";
	            	String tbl_txt = "";
	            	for (k=0;k<tbl_rows;k++) tbl_txt += row_txt;
	            	tbl_txt = "<table style=\"width: 100%\" border=\"1\"><tbody>" + tbl_txt + "</tbody></table>";
	            	insertTextAtCursor(tbl_txt);
	            });
	            
	        }
	      });
          imgButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent arg0) {
	        	byte[] fileContent;
	        	FileChooser fc = new FileChooser();
	        	fc.setTitle("Open Resource File");
	            FileChooser.ExtensionFilter extFilterJPG = 
	                    new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg",
	                                                    "*.JPEG", "*.jpeg");
	    	    FileChooser.ExtensionFilter extFilterPNG = 
	                    new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
	            fc.getExtensionFilters().addAll(extFilterJPG,extFilterPNG);
	        	File fp = fc.showOpenDialog(primStage);

	        	if (fp!=null) {
						try {
				            //check if file is too big
				            if (fp.length() > 1024 * 1024) {
				                throw new VerifyError("File is too big.");
				            }
				            //get mime type of the file
				            String type = java.nio.file.Files.probeContentType(fp.toPath());
				            //LastSelectedImage =fp.toPath;
				            BufferedImage img = ImageIO.read(fp);
				            int w = img.getWidth();
				            int h = img.getHeight();
				            int perc = 100;
				            if (w>my_width) { perc = my_width*100/w; w /= perc; h/=perc; }
				            System.out.println(w + "," + h);
				            //get html content
				            fileContent = Files.readAllBytes(fp.toPath());
				            String base64data = java.util.Base64.getEncoder().encodeToString(fileContent);
				            //String htmlData = String.format("<a href=\"\"></a>", type, base64data, type);
				            String htmlData = String.format(
				                    "<br><embed width='%d' height='%d' src='data:%s;base64,%s' type='%s' /><br>",
				                    w,h, type, base64data, type);
							insertTextAtCursor(htmlData);
							System.out.println("img path = " + fp.toPath().toString());
							images.add(fp.toPath().toString());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	
	        	}
	        }
	      });
          //========
          pdfButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent arg0) {
	            Dialog<Pair<String, String>> ndialog = new Dialog<>();
	            ndialog.setTitle("Select a file and its alias");
	            ndialog.initOwner(main_dialog);
	            ndialog.initModality(Modality.WINDOW_MODAL);

	            ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
	            ndialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

	            GridPane gridPane = new GridPane();
	            gridPane.setHgap(10);
	            gridPane.setVgap(10);
	            gridPane.setPadding(new Insets(20, 150, 10, 10));
	            
	            gridPane.add(new Label("Alias:"), 0, 0);
	            TextField given_alias = new TextField();
	            given_alias.setEditable(true);
	            given_alias.setPrefColumnCount(35);
	            given_alias.setPromptText("Enter your alias");
	            gridPane.add(given_alias, 1, 0);
	                      
	            gridPane.add(new Label("File:"), 0, 1);
	            TextField given_file = new TextField();
	            given_file.setEditable(false);
	            given_file.setPrefColumnCount(35);
	            given_file.setPromptText("Selected file");
	            gridPane.add(given_file, 1, 1);	            
	            
	            
	            Button file_btn = new Button("...");
	            gridPane.add(file_btn,2,1);
	            
	            file_btn.setOnAction(new EventHandler<ActionEvent>() {
	                @Override public void handle(ActionEvent arg0) {
	    	        	FileChooser fc = new FileChooser();
	    	        	fc.setTitle("Open Resource File");
	    	    	    FileChooser.ExtensionFilter extFilterPNG = 
	    	                    new FileChooser.ExtensionFilter("pdf files (*.pdf)", "*.pdf");
	    	            fc.getExtensionFilters().addAll(extFilterPNG);
	    	        	File fp = fc.showOpenDialog(primStage);
	    	        	if (fp!=null) given_file.setText(fp.getAbsolutePath());
	                }
	            });

	            ndialog.getDialogPane().setContent(gridPane);
	            
	            
	            // Request focus on the username field by default.
	            Platform.runLater(() -> given_alias.requestFocus());

	            ndialog.setResultConverter(dialogButton -> {
	                if (dialogButton == loginButtonType) {
	                	if ((given_alias.getText().length()==0) ||  (given_file.getText().length()==0)) return null;
	                    return new Pair<String, String>(given_alias.getText(), given_file.getText());
	                }
	                return null;
	            });  	
	            
	            //loginButtonType.disableProperty().bind(given_alias.textProperty().isEmpty());

	            Optional<Pair<String, String>> result = ndialog.showAndWait();
	            
	            result.ifPresent(pair -> {
	            	String alias = pair.getKey();
	            	String filepath = pair.getValue();
	            	System.out.println("alias = " + alias + "fpath = " + filepath);
	            	String to_be_replaced = file_replace_str1 + (file_no++) + file_replace_str2;
	            	System.out.println("pdf path = " + filepath);
	            	files.add(filepath);
	  	        	//WebView webView = (WebView) ed.lookup("WebView");
	  	        	//String selected = (String) webView.getEngine().executeScript("window.getSelection().toString();");
	  	        	String pdflinkHtml = "<a href=\"" + to_be_replaced + "\" title=\"" + alias + "\" target=\"_blank\">" + alias + "</a>";
	  	        	insertTextAtCursor(pdflinkHtml);
	            });

	        }
	      });          
          //========
          lnkButton.setOnAction(new EventHandler<ActionEvent>() {
  	        @Override public void handle(ActionEvent arg0) {
  	        	//String url = JOptionPane.showInputDialog("Enter Url");
	            Dialog<String> ndialog = new Dialog<>();
	            ndialog.setTitle("Give the URL of the link");
	            ndialog.initOwner(main_dialog);
	            ndialog.initModality(Modality.WINDOW_MODAL);

	            ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
	            ndialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

	            GridPane gridPane = new GridPane();
	            gridPane.setHgap(10);
	            gridPane.setVgap(10);
	            gridPane.setPadding(new Insets(20, 150, 10, 10));
	            
	            gridPane.add(new Label("URL:"), 0, 0);
	            TextField given_url = new TextField();
	            given_url.setEditable(true);
	            given_url.setPrefColumnCount(35);
	            given_url.setPromptText("Enter your comment.");
	            gridPane.add(given_url, 1, 0);

	            ndialog.getDialogPane().setContent(gridPane);
	            
	            // Request focus on the url field by default.
	            Platform.runLater(() -> given_url.requestFocus());

	            ndialog.setResultConverter(dialogButton -> {
	                if (dialogButton == loginButtonType) {
	                    return given_url.getText();
	                }
	                return null;
	            });

	            Optional<String> result = ndialog.showAndWait();
	            
	            result.ifPresent(url -> {
	  	        	if (url==null) return;
	  	        	String prefix = "http://";
	  	        	String sprefix = "https://";
	  	        	if (!url.startsWith(prefix)||!url.startsWith(sprefix)) url = prefix + url;
	  	        	WebView webView = (WebView) ed.lookup("WebView");
	  	        	String selected = (String) webView.getEngine().executeScript("window.getSelection().toString();");
	  	        	String hyperlinkHtml = "<a href=\"" + url.trim() + "\" title=\"" + selected + "\" target=\"_blank\">" + selected + "</a>";
	  	        	insertTextAtCursor(hyperlinkHtml);
	            	/*
	                //System.out.println("From=" + pair.getKey() + ", To=" + pair.getValue() + "prepare html table");
	            	int tbl_rows = pair.getKey();
	            	int tbl_cols = pair.getValue();
	            	String row_txt = "";
	            	int k;
	            	int width_per_column = 100/tbl_cols;
	            	for (k=0;k<tbl_cols;k++) row_txt += "<td style= height:25px;\"width:" + width_per_column + "%\"></td>";
	            	row_txt = "<tr>" + row_txt + "</tr>";
	            	String tbl_txt = "";
	            	for (k=0;k<tbl_rows;k++) tbl_txt += row_txt;
	            	tbl_txt = "<table style=\"width: 100%\" border=\"1\"><tbody>" + tbl_txt + "</tbody></table>";
	            	insertTextAtCursor(tbl_txt);*/
	            });
  	        }
  	      });
	    }
		
		
        root.getChildren().add(ed);
        Button sv_btn = new Button("Save");
        sv_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent arg0) {
            	html_code = ed.getHtmlText();
            	System.out.println(html_code);
            }
        });
        root.getChildren().add(sv_btn);
        dialog.setScene(scene);
        dialog.initOwner(primStage);
        dialog.initModality(Modality.WINDOW_MODAL); 
        dialog.showAndWait();
        return (scene);
    }    
    private static void insertTextAtCursor(String txt) {
    	//System.out.println("\n\n:---->\n"+txt);
          WebView webView = (WebView) ed.lookup(".web-view");
          WebPage webPage = Accessor.getPageFor(webView.getEngine());
          webPage.executeCommand("insertHTML", txt);
    }
}