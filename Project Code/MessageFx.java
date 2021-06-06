package schoolink;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import jfxtras.scene.control.CalendarPicker;
import javafx.stage.FileChooser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;
import javafx.beans.value.*;

public class MessageFx extends Application {
	
	private MessageFx instance;
	private int cnt;
	
	private String html_code;
	
	private int my_width;
	private int my_height;
	private int my_kind;
	private String my_title_txt;
	private String my_init_html_link;
	private HTMLEditor ed;
	private Scene parent_scene;
	private Stage primStage;
	private Stage main_dialog;
	private int file_no; 
	
	private static String file_replace_str1 = "_%$_##";
	private static String file_replace_str2 = "%$_##_";
	private static int NAMES_PER_LINE = 2;
	
	private Label to_lbl;
	
	private ArrayList<String> files;
	private int file_cnt;
	private ArrayList<String> images;
	private int image_cnt;
	private  HashMap<TreeItem<String>, String> queries;
	private TableView<Person> tableView;
	
	private Alert ok_alert;

	private VBox emailto_vbox;
	private VBox emailcc_vbox;
	private VBox selected_vbox;
	
	private Button to_btn, cc_btn, sv_btn, reply_btn;
	private TextField msg_subject;

	private HashSet<Integer> SetTo, SetCc;
	private String id_prefix;
	private Scene scene;
	
	private Stage dialog;
	
	
    public void start(Stage primaryStage) throws Exception {
    	primStage = primaryStage;
    }
	
	public MessageFx(int width, int height, String title_txt, String init_html_content_link, int kind) {
		instance = this;
    	file_no = 0;
    	files = new ArrayList<String>();
    	images = new ArrayList<String>();
    	my_width = width;
    	my_height = height;
    	my_title_txt = title_txt;
    	my_init_html_link = init_html_content_link;
    	my_kind = kind;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });		
	}

    private  void initAndShowGUI() {
        final JFXPanel fxPanel = new JFXPanel();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
       });
    }

    private  void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
    	parent_scene = createScene();
        fxPanel.setScene(parent_scene);
    }

    private Scene createScene() {
    	dialog = new Stage();
    	SetTo = new HashSet<Integer>();
    	SetCc = new HashSet<Integer>();
    	main_dialog = dialog;
    	cnt=0;
    	
        VBox root = new VBox();      
        root.setPadding(new Insets(8, 8, 8, 8));
        root.setSpacing(5);
        root.setAlignment(Pos.BOTTOM_LEFT);
        //Group  root  =  new  Group();
        scene  =  new  Scene(root, Color.ALICEBLUE);

        
        Label title = new Label(my_title_txt);
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,20));
        title.setTextFill(Color.YELLOW);
        root.getChildren().add(title);
        
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        
        
        Label subject_lbl = new Label("Θέμα:");
        subject_lbl.setFont(Font.font("verdana", 14));
        subject_lbl.setTextFill(Color.YELLOW);
        gridPane.add(subject_lbl, 0, 0);
        
        msg_subject = new TextField();
        msg_subject.setEditable(true);
        msg_subject.setPrefColumnCount(35);
        msg_subject.setPromptText("Enter your subject");
        gridPane.add(msg_subject, 1, 0);

        to_lbl = new Label("Προς");
        to_lbl.setPrefWidth(80);
        to_lbl.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,16));
        to_lbl.setTextFill(Color.YELLOW);
        gridPane.add(to_lbl, 0, 1);

        emailto_vbox = new VBox();
        ScrollPane email_to_scrpane = new ScrollPane();
        email_to_scrpane.setPrefViewportHeight(55);
        email_to_scrpane.setContent(emailto_vbox);
        gridPane.add(email_to_scrpane, 1, 1);
                
        to_btn = new Button("To:");
        to_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
            	selected_vbox = emailto_vbox;
            	id_prefix="to";
            	GetRecipients();
            }
        });
        gridPane.add(to_btn, 2, 1);
    	if (my_kind==1) {
    		Label cc_lbl = new Label("Προθεσμία:");
    		cc_lbl.setFont(Font.font("verdana", 12));
    	
    		cc_lbl.setTextFill(Color.YELLOW);
    		gridPane.add(cc_lbl, 0, 2);
    		
    		GridPane gp = new GridPane();
    		Label l = new Label("no date selected");
    		DatePicker d = new DatePicker();
    		d.setValue(LocalDate.now().plusDays(7));

    	    d.setShowWeekNumbers(true);

   	        gp.add(d, 0, 0);
   	        
   	        ComboBox<String> hourcbx = new ComboBox<String>();
   	        hourcbx.getItems().addAll(
   	            "00", "01", "02", "03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"
   	        );
   	        hourcbx.setValue("23");
   	        gp.add(hourcbx, 1, 0);
   	        ComboBox<String> mincbx = new ComboBox<String>();
   	        mincbx.getItems().addAll(
   	            "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"
   	        );
   	        mincbx.setValue("55");
	        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
   	            public void handle(ActionEvent e)  {
    	                LocalDate i = d.getValue();
    	                l.setText("Date :" + i);
    	                //System.out.println("--->" + d.getValue() + "->" + hourcbx.getValue() + ":" + mincbx.getValue()); 
    	            }
    	    };
       	    d.setOnAction(event);
   	        gp.add(mincbx, 2, 0);
   	        gridPane.add(gp, 1, 2);
   	     
    	} else {
    		Label cc_lbl = new Label("Cc:");
    		cc_lbl.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,16));
    		cc_lbl.setTextFill(Color.YELLOW);
    		gridPane.add(cc_lbl, 0, 2);
    		emailcc_vbox = new VBox();
    		ScrollPane email_cc_scrpane = new ScrollPane();
    		email_cc_scrpane.setPrefViewportHeight(55);
    		email_cc_scrpane.setContent(emailcc_vbox);
    		gridPane.add(email_cc_scrpane, 1, 2);
            cc_btn = new Button("Cc:");
            cc_btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent event) {
                	selected_vbox = emailcc_vbox;
                	id_prefix="cc";
                	GetRecipients();
                }
            });
            gridPane.add(cc_btn, 2, 2);
        }

        
        ImageView img = new ImageView(new Image(MessageFx.class.getResource("/images/assign.png").toExternalForm(), 128, 128, true, true));
        gridPane.add(img,4,0,2,2);
        
        root.getChildren().add(gridPane);
        
        ed = new HTMLEditor();
        ed.setPrefWidth(my_width);
        ed.setPrefHeight(my_height-100);

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

	            Platform.runLater(() -> {
	            	rows.requestFocus();
	        	});

	            ndialog.setResultConverter(dialogButton -> {
	                if (dialogButton == loginButtonType) {
	                    return new Pair<Integer, Integer>(rows.getValue(), cols.getValue());
	                }
	                return null;
	            });

	            Optional<Pair<Integer, Integer>> result = ndialog.showAndWait();

	            result.ifPresent(pair -> {
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
				            //System.out.println(w + "," + h);
				            //get html content
				            //fileContent = Files.readAllBytes(fp.toPath());
				            //String base64data = java.util.Base64.getEncoder().encodeToString(fileContent);
				            //String htmlData = String.format(
				            //        "<br><embed width='%d' height='%d' src='data:%s;base64,%s' type='%s' /><br>",
				            //        w,h, type, base64data, type);
				            String os_file_path = fp.toPath().toString();
				            String html_file_path = os_file_path.replaceAll("\\\\", "/");
				            //System.out.println(File.pathSeparator + "<== img path = " + os_file_path + ", " + html_file_path);
				            String htmlData = "<img src=\"file://"+ html_file_path + "\" width=\"" + w + "\" height=\"" + h + "\" alt=\"" + image_cnt +"\" title=\"\" />";
				            insertTextAtCursor(htmlData);
							image_cnt++;
							images.add(os_file_path);
						} catch (IOException e) {
							e.printStackTrace();
						}
			        	
	        	}
	        }
	      });
          Platform.runLater(() -> {
              if (my_init_html_link!=null) {
            	  	LoadMessage();
                }
              	ArrangeLinkInEditor((WebView) ed.lookup(".web-view"));
              	scene.setCursor(Cursor.DEFAULT);
              	//db_interface.multirow_form.setCursor(db_interface.default_cursor);
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
	            
	            
	            Platform.runLater(() -> given_alias.requestFocus());

	            ndialog.setResultConverter(dialogButton -> {
	                if (dialogButton == loginButtonType) {
	                	if ((given_alias.getText().length()==0) ||  (given_file.getText().length()==0)) return null;
	                    return new Pair<String, String>(given_alias.getText(), given_file.getText());
	                }
	                return null;
	            });  	
	            

	            Optional<Pair<String, String>> result = ndialog.showAndWait();
	            
	            result.ifPresent(pair -> {
	            	String alias = pair.getKey();
	            	String filepath = pair.getValue();
	            	//System.out.println("alias = " + alias + "fpath = " + filepath);
	            	String to_be_replaced = file_replace_str1 + (file_no++) + file_replace_str2;
	            	//System.out.println("pdf path = " + filepath);
	            	files.add(filepath);
	  	        	String pdflinkHtml = "<a href=\"" + to_be_replaced + "\" title=\"" + alias + "\" target=\"_blank\">" + alias + "</a>";
	  	        	insertTextAtCursor(pdflinkHtml);
	            });

	        }
	      });          
          //========
          lnkButton.setOnAction(new EventHandler<ActionEvent>() {
  	        @Override public void handle(ActionEvent arg0) {
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
	            });
  	        }
  	      });
	    }
        root.getChildren().add(ed);

	    GridPane gridPane1 = new GridPane();
        gridPane1.setHgap(10);
        gridPane1.setVgap(10);
        gridPane1.setPadding(new Insets(20, 150, 10, 10));
        
	    ImageView gr1 = new ImageView(new Image(MessageFx.class.getResource("/images/send_msg.png").toExternalForm(), 32, 32, true, true));    
        sv_btn = new Button("", gr1);
        sv_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent arg0) {
            	Platform.runLater(() -> {
            		SendMessage();
    	        });
            }
        });
        
	    ImageView gr3 = new ImageView(new Image(MessageFx.class.getResource("/images/reply.png").toExternalForm(), 32, 32, true, true));    
        reply_btn = new Button("", gr3);
        reply_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent arg0) {
            	Platform.runLater(() -> {
            		System.out.println("aHERE");
            		Cval.reply_to = -125;
            		new MessageFx(Cval.ScreenWidth, Cval.ScreenHeight, "Απάντηση",null, 4);
    	        });
            }
        });
        
	    ImageView gr2 = new ImageView(new Image(MessageFx.class.getResource("/images/exit.png").toExternalForm(), 32, 32, true, true));    
        Button cancel_btn = new Button("", gr2);
        cancel_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
            	((Node)(event.getSource())).getScene().getWindow().hide();
            }
        });
        gridPane1.add(sv_btn, 0, 0);
        sv_btn.setTooltip(new Tooltip("Αποστολή"));
        gridPane1.add(reply_btn, 1, 0);
        reply_btn.setVisible(false);
        reply_btn.setTooltip(new Tooltip("Απάντηση"));
        cancel_btn.setTooltip(new Tooltip("Έξοδος"));
        gridPane1.add(cancel_btn, 2, 0);
        root.getChildren().add(gridPane1);
        
        dialog.setScene(scene);
        dialog.setHeight(700);
        dialog.setWidth(900);
        
        root.setBackground(new Background(
        		new BackgroundImage(
	                new Image(MessageFx.class.getResource("/images/main_bg.png").toExternalForm()),
	                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
	                new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
	                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
	             )
        ));
        
        dialog.initOwner(primStage);
        dialog.initModality(Modality.WINDOW_MODAL); 
        dialog.showAndWait();
        //dialog.show();
        return (scene);
    }    
    private void insertTextAtCursor(String txt) {
          WebView webView = (WebView) ed.lookup(".web-view");
          WebPage webPage = Accessor.getPageFor(webView.getEngine());
          webPage.executeCommand("insertHTML", txt);
    }
    
    @SuppressWarnings("unchecked")
	private void GetRecipients() {
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
        	sql_string = "SELECT u.id as id, surname, firstname, email  FROM users as u INNER JOIN participates as p on u.id=p.user_id INNER JOIN GROUPS as g on p.group_id = g.id WHERE g.class_id = " + parts[0];
        	queries.put(tit, sql_string);
        	ti.getChildren().add(tit);
        }
        tit = new TreeItem<String>("Όλοι οι μαθητές");
        sql_string = "SELECT id, surname, firstname, email  FROM users WHERE role_id=4";  
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
        if (my_kind!=1) {
	        ti = new TreeItem<String>("Γονείς");
	        for (k=0;k<classes.size();k++) {
	        	String parts[] = classes.get(k).split(",");
	        	tit = new TreeItem<String>("Γονείς " + parts[1]);
	        	sql_string = "SELECT u.id as id, surname, firstname, email  FROM users as u INNER JOIN participates as p on u.id=p.user_id INNER JOIN GROUPS as g on p.group_id = g.id WHERE u.role_id=5 and g.class_id = " + parts[0];
	        	queries.put(tit, sql_string);
	        	ti.getChildren().add(tit);
	        }
	        for (k=0;k<sub_classes.size();k++) {
	        	String parts[] = sub_classes.get(k).split(",");
	        	tit = new TreeItem<String>("Γονείς " + parts[1]);
	        	sql_string = "SELECT u.id as id, surname, firstname, email  FROM users as u INNER JOIN participates as p on u.id=p.user_id INNER JOIN GROUPS as g on p.group_id = g.id WHERE u.role_id=5 and g.id = " + parts[0];
	        	queries.put(tit, sql_string);
	        	ti.getChildren().add(tit);
	        }
	        tit = new TreeItem<String>("Όλοι οι γονείς");
	        sql_string = "SELECT id, surname, firstname, email  FROM users WHERE role_id=5";  
	        queries.put(tit, sql_string);
	        ti.getChildren().add(tit);
	        rootItem.getChildren().add(ti);
	
	        ti = new TreeItem<String>("Εκπαιδετικοί");
	        for (k=0;k<classes.size();k++) {
	        	String parts[] = classes.get(k).split(",");
	        	tit = new TreeItem<String>("Εκπαιδετικοί " + parts[1]);
	        	sql_string = "SELECT u.id as id, surname, firstname, email  FROM users as u INNER JOIN participates as p on u.id=p.user_id INNER JOIN GROUPS as g on p.group_id = g.id WHERE g.class_id = " + parts[0];
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
	        sql_string = "SELECT id as id, surname, firstname, email  FROM users WHERE id>0 and role_id=3";  
	        queries.put(tit, sql_string);
	        ti.getChildren().add(tit);
	        rootItem.getChildren().add(ti);
	        
	        tit = new TreeItem<String>("Όλοι");
	        ti = new TreeItem<String>("Όλοι οι χρήστες");
	        sql_string = "SELECT id, surname, firstname, email  FROM users WHERE id>0";  
	        queries.put(tit, sql_string);
	        ti.getChildren().add(tit);
	        rootItem.getChildren().add(ti);  
	        ti.setExpanded(true);     
        }

        rootItem.setExpanded(true);

        TreeView<String> tree = new TreeView<String>(rootItem);   
        MultipleSelectionModel msm = tree.getSelectionModel();

        int row = tree.getRow(tit);
        
        tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	String query_str=queries.get(newValue);
        	if (query_str!=null) {
        		//System.out.println("JFX--->" + query_str);
        		LoadTableView(query_str);
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
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column1);

        tableView.getColumns().add(column3);
        
        msm.select(row);
        
        gridPane.add(tree, 1, 0);
        gridPane.add(tableView, 2, 0);
    	
	    ImageView graphic1 = new ImageView(new Image(MessageFx.class.getResource("/images/select_all.png").toExternalForm(), 16, 16, true, true));
        Button select_all_btn = new Button("", graphic1);
    	select_all_btn.setTooltip(new Tooltip("Επιλογή όλων")); 
    	select_all_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent arg0) {
            	for (Person p : tableView.getItems()) 
            		p.setSelected(true);
            }
    	});
	    ImageView graphic2 = new ImageView(new Image(MessageFx.class.getResource("/images/select_none.png").toExternalForm(), 16, 16, true, true));
        Button select_none_btn = new Button("", graphic2);
    	select_none_btn.setTooltip(new Tooltip("Μηδενισμός επιλογών"));
    	select_none_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent arg0) {
            	for (Person p : tableView.getItems()) 
            		p.setSelected(false);            	
            }
    	});
    	VBox btns = new VBox();
    	btns.setPadding(new Insets(8, 8, 8, 8));
    	btns.setSpacing(5);

    	btns.getChildren().addAll(select_all_btn, select_none_btn);
        gridPane.add(btns,3,0);
 
        Platform.runLater(() -> tree.requestFocus());

        ndialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
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
        	String names = recipients.getKey();
        	String ids = recipients.getValue();
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
            	email_to.setId(lid);
            	
            	if (id_prefix.equals("to")) SetTo.add(Integer.parseInt(rec_ids[j]));
            	else  SetCc.add(Integer.parseInt(rec_ids[j]));
            	
            	email_to.prefColumnCountProperty().bind(email_to.textProperty().length());
            	
            	Button x_btn = new Button("X");
            	x_btn.setId(id_prefix + "@" + rec_ids[j]+"_"+2);
            	 
            	x_btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent arg0) {
                    	String sid = x_btn.getId();
                    	
                    	String prefix=sid.substring(0, sid.indexOf("@"));
                    	String id = sid.substring(sid.indexOf("@")+1, sid.indexOf("_"));
                    	SetTo.add(Integer.parseInt(id));
                    	//System.out.println(prefix + "->" + id);
                    	
                    	String lid = id_prefix+"@"+id+"_"+1;
                    	TextField txt_field = (TextField) scene.lookup("#"+lid);
                    	
                    	if (id_prefix.equals("to")) SetTo.remove(Integer.parseInt(id));
                    	else  SetCc.remove(Integer.parseInt(id));
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
	private void LoadTableView(String sql_query) {
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
			e.printStackTrace();
		}
    }
    
    private void LoadMessage() {
    	ed.setHtmlText(FileServer.DownloadTxtFile(my_init_html_link));
    	TextArea textAreaTo = new TextArea(); textAreaTo.setPrefHeight(400); textAreaTo.setPrefWidth(600);
    	
    	emailto_vbox.getChildren().add(textAreaTo);
    	if (my_title_txt.equals("Εισερχόμενο μήνυμα")) {
    		to_lbl.setText("Από");
    		int row, col;
    		MultirowForm mr = Cval.multirow_instances_stack.peek();
    		row = mr.clicked_row;
    		col = mr.column_sender_id;
    		System.out.println("===>" + mr.db_table.getModel().getValueAt(row, col+1).toString());
    		textAreaTo.setText(mr.db_table.getModel().getValueAt(row, col).toString());
    	} else {
    		System.out.println("HEHEHEHEHE");
    		textAreaTo.setText(db_interface.last_msg_to);
    	}
    	if (my_kind!=1) {
    		TextArea textAreaCc = new TextArea(); textAreaCc.setPrefHeight(400); textAreaCc.setPrefWidth(600);
    		textAreaCc.setText(db_interface.last_msg_cc);
        	emailcc_vbox.getChildren().add(textAreaCc);
    	}
    	msg_subject.setText(db_interface.last_msg_subject);
    	to_btn.setVisible(false); 
        reply_btn.setVisible(true);
    	if (my_kind!=1) {
    		cc_btn.setVisible(false);
    		sv_btn.setVisible(false);
    	}
    }
    
    private void ArrangeLinkInEditor(WebView webview) {  
	    webview.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
	    	  @Override
	    	  public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
	    	    Platform.runLater(() -> {
	    	    	cnt++;
	    	        if (cnt>4) webview.getEngine().getLoadWorker().cancel();
	    	    });
	    	  }
	    	});
	
	    	webview.getEngine().locationProperty().addListener(new ChangeListener<String>() {
	    	  @Override
	    	  public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	    		  FileServer.OpenUrl(newValue);
	    	  }
	    	});
    }
    
    private String ReplaceImagesAndFiles(String html_in) {
    	//String html_out="";
    	//System.out.println("<==" + html_in);
    	String html_out = html_in.replace(" contenteditable=\"true\"", "");
    	//System.out.println("==>" + html_out);
    	int k;
    	for (k=0;k<files.size();k++) {
    		//System.out.println("Uploading file:"+k + "=" + files.get(k));
    		StringPair p = FileServer.UploadPdf(files.get(k));
    		String to_be_replaced = file_replace_str1 + k + file_replace_str2;
    		html_out = html_out.replace(to_be_replaced, p.link);
    	}
    	for (k=0;k<images.size();k++) {
    		//System.out.println("Uploading image:"+k + "=" + images.get(k));
    		StringPair p =FileServer.UploadImage(images.get(k));
    		int fpos = html_out.lastIndexOf("file://",  html_out.indexOf("alt=\"" + k + "\""));
    		int rpos = html_out.indexOf("\" width", fpos);
    		html_out = html_out.substring(0, fpos) + p.link.replace("?dl=0", "?raw=1") + html_out.substring(rpos);
    	}
    	return html_out;
    }
    
    private void SendMessage() {
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
    	scene.setCursor(Cursor.WAIT);
    	Task<Void> task = new Task<Void>() {
    	    @Override
    	    public Void call() {
    	        StringPair sp=null;
    	        try {
    	        	//System.out.println("S1");
    	        	html_code = ReplaceImagesAndFiles(ed.getHtmlText());
    	        	//System.out.println("S2");
    	        	sp = FileServer.WriteHtmlToFile(html_code);
    	        	//System.out.println("S3");
    	        	if (sp.link==null) throw new Exception();
    	       		String file_link = sp.id.substring(3); //exclude id:
    	       	   	String sql_str_msg = "INSERT INTO msgs VALUES(DEFAULT, CURRENT_TIMESTAMP, '" + msg_subject.getText() +"','"+ file_link +"', NULL, NULL," + my_kind +")";
    	       	   	db_interface.execute(sql_str_msg);
    	       	   	int msg_id = db_interface.last_inserted_id("msgs");
    	           	String values = "";
    	           	String sql_str_to = "INSERT INTO msgs_details VALUES ";
    	           	for(int id : SetTo){
    	      		   values += ",(" + msg_id+ "," + db_interface.user_id + ", " + id +", TRUE, NULL)";
    	           	}
    	           	String final_to_sql = sql_str_to + values.substring(1);
    	           	values="";
    	           	for(int id : SetCc){
    	       		   values += ",(" + msg_id+ "," + db_interface.user_id + ", " + id +", FALSE, NULL)";
    	       		}
    	           	//System.out.println("S4:>" + final_to_sql + "\n" + values + "<<<<<");
    	           	String final_cc_sql = "";
    	           	if (!values.equals("")) {
    	           		final_cc_sql = sql_str_to + values.substring(1);
    	           		db_interface.execute(final_to_sql, final_cc_sql);
    	           	} else db_interface.execute(final_to_sql);
    	           	Platform.runLater(
    	           		  () -> {
    	      	       		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	    	       		alert.setContentText("Message sent succesfully");
    	    	    		alert.showAndWait().ifPresent(response -> {
    	    	    		    if (response == ButtonType.OK ) {
    	    	    		    	Platform.runLater(() -> {dialog.close();});
    	    	    		    }
    	    	    		});;
    	    	    		
    	           		  }
    	           		);  	       		
    			} catch (SQLException e) { 
    				e.printStackTrace();
    	       		Alert alert = new Alert(Alert.AlertType.ERROR);
    	       		alert.setContentText("Failed1!!!!!!");
    	       		FileServer.DeleteFile(sp.id);
    	       		alert.show();
    			} catch (Exception e) { 
    				e.printStackTrace();
    	       		Alert alert = new Alert(Alert.AlertType.ERROR);
    	       		alert.setContentText("Failed2!!!!!!");
    	       		alert.show();
    			}
    	        return null;
    	    }
    	};
    	task.setOnSucceeded(evnt -> {scene.setCursor(Cursor.DEFAULT);/*Platform.exit();*/});
        task.setOnFailed(evnt -> { System.out.println("Task failed!");});
    	new Thread(task).start();
    }
}