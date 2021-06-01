package schoolink;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;

public class MessageFx extends Application {
	
	public static MessageFx instance;
	
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
	private static int NAMES_PER_LINE = 2;
	private static ArrayList<String> files;
	private static ArrayList<String> images;
	private static String addresses;
	private static HashMap<TreeItem<String>, String> queries;
	private static TableView<Person> tableView;
	//private static int to_recipients_count;
	//private static int cc_recipients_count;

	//private static ScrollPane email_to_scrpane;
	private static VBox emailto_vbox;
	//private static ScrollPane email_cc_scrpane;
	private static VBox emailcc_vbox;
	private static VBox selected_vbox;

	private static HashSet<Integer> SetTo, SetCc;
	private static String id_prefix;
	private static Scene scene;
	
	
    public void start(Stage primaryStage) throws Exception {
    	primStage = primaryStage;
    }
	
	public MessageFx(int width, int height, String title_txt, String init_html_content) {
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
    	SetTo = new HashSet<Integer>();
    	SetCc = new HashSet<Integer>();
    	main_dialog = dialog;
    	try {
    		URL.setURLStreamHandlerFactory(protocol -> {
    			if (protocol.startsWith("http")) {
    				return new CustomUrlHandler();
    			}
    			return null;
    		});
    	} catch (Error e) {
    	    //e.printStackTrace();
    		System.err.println("Url handlig already installed...");
    	}
        
        VBox root = new VBox();      
        root.setPadding(new Insets(8, 8, 8, 8));
        root.setSpacing(5);
        root.setAlignment(Pos.BOTTOM_LEFT);
        //Group  root  =  new  Group();
        scene  =  new  Scene(root, Color.ALICEBLUE);

        Text  text  =  new  Text();
       
        text.setX(40);
        text.setY(100);
        text.setFont(new Font(20));
        text.setText(my_title_txt);
        root.getChildren().add(text);
        
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        
        gridPane.add(new Label("Subject:"), 0, 0);
        TextField msg_subject = new TextField();
        msg_subject.setEditable(true);
        msg_subject.setPrefColumnCount(35);
        msg_subject.setPromptText("Enter your subject");
        gridPane.add(msg_subject, 1, 0);


        gridPane.add(new Label("To:"), 0, 1);

        VBox emailto_vbox = new VBox();
        ScrollPane email_to_scrpane = new ScrollPane();
        email_to_scrpane.setPrefViewportHeight(55);
        email_to_scrpane.setContent(emailto_vbox);
        gridPane.add(email_to_scrpane, 1, 1);
        
        
        
        Button to_btn = new Button("To:");
        to_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
            	selected_vbox = emailto_vbox;
            	id_prefix="to";
            	GetRecipients();
            }
        });
        gridPane.add(to_btn, 2, 1);

        gridPane.add(new Label("Cc:"), 0, 2);
        emailcc_vbox = new VBox();
        ScrollPane email_cc_scrpane = new ScrollPane();
        email_cc_scrpane.setPrefViewportHeight(55);
        email_cc_scrpane.setContent(emailcc_vbox);
        gridPane.add(email_cc_scrpane, 1, 2);
       
        Button cc_btn = new Button("Cc:");
        cc_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
            	selected_vbox = emailcc_vbox;
            	id_prefix="cc";
            	GetRecipients();
            }
        });
        gridPane.add(cc_btn, 2, 2);

        root.getChildren().add(gridPane);
        
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
	      ImageView graphic = new ImageView(new Image(MessageFx.class.getResource("/images/ed_table.png").toExternalForm(), 16, 16, true, true));
	      Button tblButton = new Button("", graphic);
	      ImageView graphic1 = new ImageView(new Image(MessageFx.class.getResource("/images/ed_image.png").toExternalForm(), 16, 16, true, true));
	      Button imgButton = new Button("", graphic1);
	      ImageView graphic2 = new ImageView(new Image(MessageFx.class.getResource("/images/ed_link.png").toExternalForm(), 16, 16, true, true));     
	      Button lnkButton = new Button("", graphic2);
	      ImageView graphic3 = new ImageView(new Image(MessageFx.class.getResource("/images/ed_import.png").toExternalForm(), 16, 16, true, true));     
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
	            
	            //Request focus on the url field by default.
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

	    GridPane gridPane1 = new GridPane();
        gridPane1.setHgap(10);
        gridPane1.setVgap(10);
        gridPane1.setPadding(new Insets(20, 150, 10, 10));
        
        Button sv_btn = new Button("Send");
        sv_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent arg0) {
            	if (msg_subject.getText().length()==0) {
            		Alert alert = new Alert(Alert.AlertType.WARNING);
            		alert.setContentText("Please give a subject");
            		alert.show();
            		return;
        		}
            	if (SetTo.size()==0) {
            		Alert alert = new Alert(Alert.AlertType.WARNING);
            		alert.setContentText("Recipients (to) list is empty...");
            		alert.show();
            		return;
            	}
            	for(int id : SetTo){
            		   System.out.println("To->" + id);
            		}
            	for(int id : SetCc){
         		   System.out.println("Cc->" + id);
         		}
            	html_code = ed.getHtmlText();
            	//System.out.println(html_code);
            }
        });
        Button cancel_btn = new Button("Cancel");
        cancel_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
            	((Node)(event.getSource())).getScene().getWindow().hide();
            }
        });
        gridPane1.add(sv_btn, 0, 0);
        root.getChildren().add(gridPane1);
        ///scene.set
        dialog.setScene(scene);
        dialog.setHeight(700);
        dialog.setWidth(900);
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
    
    @SuppressWarnings("unchecked")
	private static void GetRecipients() {
        Dialog<Pair<String, String>> ndialog = new Dialog<>();
        ndialog.setTitle("Select recipients");
        ndialog.initOwner(main_dialog);
        ndialog.initModality(Modality.WINDOW_MODAL);

        ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        ndialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(30);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        ndialog.getDialogPane().setContent(gridPane);
        
        queries = new HashMap<TreeItem<String>, String>();
        TreeItem<String> rootItem = new TreeItem<String> ("Κατηγορίες");
        int k;
             
        TreeItem<String> tit;
        String sql_string;
        TreeItem<String> ti; 
        ti = new TreeItem<String>("Τάξεις");
        ArrayList<String> classes = db_interface.getClasses();
        for (k=0;k<classes.size();k++) {
        	String parts[] = classes.get(k).split(",");
        	tit = new TreeItem<String>(parts[1]);
        	sql_string = "SELECT id, surname, firstname, email  FROM users as u INNER JOIN participates as p on u.id=p.user_id INNER JOIN GROUPS as g on p.group_id = g.id WHERE g.class_id = " + parts[0];
        	queries.put(tit, sql_string);
        	ti.getChildren().add(tit);
        }
        tit = new TreeItem<String>("Όλοι οι μαθητές");
        sql_string = "SELECT u.id as id, surname, firstname, email  FROM users WHERE role_id=4";  
        queries.put(tit, sql_string);
        ti.getChildren().add(tit);
        rootItem.getChildren().add(ti);
        
        ti = new TreeItem<String>("Τμήματα");
        ArrayList<String> sub_classes = db_interface.getSubClasses();
        for (k=0;k<sub_classes.size();k++) {
        	String parts[] = sub_classes.get(k).split(",");
        	tit = new TreeItem<String>(parts[1]);
        	sql_string = "SELECT u.id as id, surname, firstname, email  FROM users as u INNER JOIN participates as p on u.id=p.user_id INNER JOIN GROUPS as g on p.group_id = g.id WHERE g.id = " + parts[0];
        	queries.put(tit, sql_string);
        	ti.getChildren().add(tit);
        }
        rootItem.getChildren().add(ti);
        
        ti = new TreeItem<String>("Γονείς");
        for (k=0;k<classes.size();k++) {
        	String parts[] = classes.get(k).split(",");
        	tit = new TreeItem<String>("Γονείς " + parts[1]);
        	sql_string = "SELECT id, surname, firstname, email  FROM users as u INNER JOIN participates as p on u.id=p.user_id INNER JOIN GROUPS as g on p.group_id = g.id WHERE g.class_id = " + parts[0];
        	queries.put(tit, sql_string);
        	ti.getChildren().add(tit);
        }
        for (k=0;k<sub_classes.size();k++) {
        	String parts[] = sub_classes.get(k).split(",");
        	tit = new TreeItem<String>("Γονείς " + parts[1]);
        	sql_string = "SELECT u.id as id, surname, firstname, email  FROM users as u INNER JOIN participates as p on u.id=p.user_id INNER JOIN GROUPS as g on p.group_id = g.id WHERE g.id = " + parts[0];
        	queries.put(tit, sql_string);
        	ti.getChildren().add(tit);
        }
        tit = new TreeItem<String>("Όλοι οι γονείς");
        sql_string = "SELECT u.id as id, surname, firstname, email  FROM users WHERE role_id>0";  
        queries.put(tit, sql_string);
        ti.getChildren().add(tit);
        rootItem.getChildren().add(ti);

        ti = new TreeItem<String>("Εκπαιδετικοί");
        for (k=0;k<classes.size();k++) {
        	String parts[] = classes.get(k).split(",");
        	tit = new TreeItem<String>("Εκπαιδετικοί " + parts[1]);
        	sql_string = "SELECT id, surname, firstname, email  FROM users as u INNER JOIN participates as p on u.id=p.user_id INNER JOIN GROUPS as g on p.group_id = g.id WHERE g.class_id = " + parts[0];
        	queries.put(tit, sql_string);
        	ti.getChildren().add(tit);
        }
        for (k=0;k<sub_classes.size();k++) {
        	String parts[] = sub_classes.get(k).split(",");
        	tit = new TreeItem<String>("Εκπαιδετικοί " + parts[1]);
        	sql_string = "SELECT u.id as id, surname, firstname, email  FROM users as u INNER JOIN participates as p on u.id=p.user_id INNER JOIN GROUPS as g on p.group_id = g.id WHERE g.id = " + parts[0];
        	queries.put(tit, sql_string);
        	ti.getChildren().add(tit);
        }
        tit = new TreeItem<String>("Όλοι οι εκπαιδευτικοί");
        sql_string = "SELECT id as id, surname, firstname, email  FROM users WHERE id>0";  
        queries.put(tit, sql_string);
        ti.getChildren().add(tit);
        rootItem.getChildren().add(ti);
        
        tit = new TreeItem<String>("Όλοι");
        ti = new TreeItem<String>("Όλοι οι χρήστες");
        sql_string = "SELECT id as id, surname, firstname, email  FROM users WHERE id>0";  
        queries.put(tit, sql_string);
        ti.getChildren().add(tit);
        rootItem.getChildren().add(ti);  
        ti.setExpanded(true);
        rootItem.setExpanded(true);


     // Now the row can be selected.
        
       
        TreeView<String> tree = new TreeView<String>(rootItem);   
        MultipleSelectionModel msm = tree.getSelectionModel();

     // This line is the not-so-clearly documented magic.
        int row = tree.getRow(tit);
        
        tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	String query_str;
        	query_str = queries.get(newValue);
        	if (query_str!=null) {
        		LoadTableView(queries.get(newValue));
        	}
        });
        tableView = new TableView<Person>();
        
        TableColumn<Person, Boolean> selectedCol = new TableColumn<>("Select");
        selectedCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        selectedCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectedCol));
        selectedCol.setEditable(true);
        tableView.setEditable(true);
        
        TableColumn<Person, String> column0 = new TableColumn<>("id");
        column0.setCellValueFactory(new PropertyValueFactory<>("id"));
        column0.setEditable(false);

        TableColumn<Person, String> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        column1.setEditable(false);

        TableColumn<Person, String> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        column2.setEditable(false);
        TableColumn<Person, String> column3 = new TableColumn<>("email");
        column3.setCellValueFactory(new PropertyValueFactory<>("email"));
        column3.setEditable(false);
        
        column0.setVisible(false);
        tableView.getColumns().add(selectedCol);
        tableView.getColumns().add(column0);
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        
        msm.select(row);
        
        gridPane.add(tree, 1, 0);
        gridPane.add(tableView, 2, 0);
 
        Platform.runLater(() -> tree.requestFocus());

        ndialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
            	//System.out.println("Name");
            	String ret_names = "";
            	String ret_ids = "";
            	int m=0;
            	for (Person p : tableView.getItems()) {
            		if (p.isSelected()) {
            			m++;
            			ret_names += p.getLastName() + " " + p.getFirstName() + ";";
            			ret_ids += p.getId() + ";";
            		}
            	}
            	if (m>0) return new Pair<String, String>(ret_names, ret_ids);
            	else return null;
            }
            return null;
        });

        Optional<Pair<String, String>> result = ndialog.showAndWait();

        result.ifPresent(recipients -> {
        	//aaaaaaaaaaaaaaaaaaaaa
        	String names = recipients.getKey();
        	String ids = recipients.getValue();
        	//System.out.println(names);
        	//System.out.println(ids);
        	String[] rec_ids = ids.split(";");
        	String[] rec_names = names.split(";");
 
        	HBox hbox=null;
        	for (int j=0; j<rec_ids.length;j++) {
        		if (j%NAMES_PER_LINE==0)  hbox = new HBox();
        	   	
        		TextField email_to = new TextField();
            	email_to.setText(rec_names[j]);
            	email_to.setEditable(true);
            	email_to.setPrefColumnCount(20);
            	String lid = id_prefix+ "@" + rec_ids[j]+"_"+1;
            	//System.out.println("-->" + lid);
            	email_to.setId(lid);
            	
            	if (id_prefix.equals("to")) SetTo.add(Integer.parseInt(rec_ids[j]));
            	else  SetCc.add(Integer.parseInt(rec_ids[j]));
            	
            	email_to.prefColumnCountProperty().bind(email_to.textProperty().length());
            	
            	Button x_btn = new Button("X");
            	x_btn.setId(id_prefix + "@" + rec_ids[j]+"_"+2);
            	 
            	x_btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent arg0) {
                    	String sid = x_btn.getId();
                    	//System.out.println(sid);
                    	
                    	String prefix=sid.substring(0, sid.indexOf("@"));
                    	String id = sid.substring(sid.indexOf("@")+1, sid.indexOf("_"));
                    	SetTo.add(Integer.parseInt(id));
                    	System.out.println(prefix + "->" + id);
                    	
                    	String lid = id_prefix+"@"+id+"_"+1;
                    	//System.out.println("<-----" + lid);
                    	TextField txt_field = (TextField) scene.lookup("#"+lid);
                    	//System.out.println(txt_field.getText());
                    	
                    	if (id_prefix.equals("to")) SetTo.remove(Integer.parseInt(id));
                    	else  SetCc.add(Integer.parseInt(id));
                    	x_btn.setVisible(false); x_btn.setManaged(false);
                    	txt_field.setVisible(false); txt_field.setManaged(false);
                    }
                });
            	
            	HBox.setMargin(email_to, new Insets(2, 2, 2, 2)); 
            	HBox.setMargin(x_btn, new Insets(2, 2, 2, (j%NAMES_PER_LINE==NAMES_PER_LINE-1) ? 5:2)); 
            	hbox.getChildren().addAll(email_to, x_btn);
            	if (j%NAMES_PER_LINE>0) selected_vbox.getChildren().add(hbox);
        	}	
        	if (rec_ids.length%2>0) selected_vbox.getChildren().add(hbox);
        });
    }
    
    @SuppressWarnings("unchecked")
	private static void LoadTableView(String sql_query) {
    	//System.out.println("Selected query : " + sql_query);
    	try {
    		tableView.getItems().clear();
    		db_interface.getQueryResults(sql_query);
    		if (db_interface.sql_success) {
    			ResultSet lrs = db_interface.rs;
        		while (db_interface.rs.next()) {
        			tableView.getItems().add(new Person(lrs.getInt(1), lrs.getString("firstname"), lrs.getString("surname"), lrs.getString("email")));
				}
        		lrs.close();
        		db_interface.last_stmt.close();
    		} 
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}