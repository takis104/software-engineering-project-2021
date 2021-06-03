package schoolink;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.CreateFolderErrorException;
import com.dropbox.core.v2.files.DownloadErrorException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;

public class FileServer {
    private static String year;
    public static String LastSelectedImage;
    public static File SavedFile;
	
	public static StringPair storeTmpData() {
		return UploadFile("/2021/docs/msg_4_2021_04_22_17_44_07_aaaabbbaaa.html", "e:\\test1.txt");
	}
	
	public static StringPair UploadFile(String online_filename, String local_path_to_file) {
		FileMetadata metadata1;
		StringPair ret_val=null;
		//System.out.println("In upload file: onlinefname="+online_filename + ",local_fname="+local_path_to_file);
		DbxClientV2 client1=new DbxClientV2(new DbxRequestConfig(Cval.PROJECT_NAME), Cval.DROPBOX_ACCESS_TOKEN);
		try {
			metadata1 = client1.files().uploadBuilder(online_filename)
						.withMode(WriteMode.OVERWRITE).
						uploadAndFinish(new FileInputStream(local_path_to_file));	
            SharedLinkMetadata meta = client1.sharing().createSharedLinkWithSettings(metadata1.getPathLower());
            ret_val = new StringPair(metadata1.getId(), meta.getUrl());
		} catch (UploadErrorException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DbxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret_val;
	}
	
    public static boolean DeleteFile(String file_id) {
    	try {
    		DbxClientV2 client=new DbxClientV2(new DbxRequestConfig(Cval.PROJECT_NAME), Cval.DROPBOX_ACCESS_TOKEN);
    		client.files().permanentlyDelete("id:" + file_id);
    		return true;
        } catch (Exception e){
        	e.printStackTrace();
        	return false;
        }
	}
    
    public static InputStream DownloadFile(String file_id) {
    	DbxDownloader<FileMetadata> downloader=null;
        try {
        	DbxClientV2 client=new DbxClientV2(new DbxRequestConfig(Cval.PROJECT_NAME), Cval.DROPBOX_ACCESS_TOKEN);
        	downloader = client.files().download("id:" + file_id);
        	final Path local_path = Files.createTempFile(Cval.PROJECT_NAME, ".html");
        	//client.files().downloadBuilder(file_id).download(local_path.get);
	        FileMetadata response = downloader.getResult();
	        //response.
	        return downloader.getInputStream();
        } catch (Exception e){
        	e.printStackTrace();
        } finally {
            downloader.close(); 
        }
        return null;    	
    }
    
    public static StringPair UploadPdf(String local_path_to_file) {
    	return (UploadFile(getOnlineFilename(".pdf"), local_path_to_file));
    }
    
    public static StringPair UploadImage(String local_path_to_file) {
    	String image_type = local_path_to_file.endsWith("png") ? ".png" : ".jpg";
    	return (UploadFile(getOnlineFilename(image_type), local_path_to_file));
    }
    

    
    public static ImageIcon GetImageToIcon(String file_id) {
    	InputStream icon_stream = DownloadFile(file_id);
    	try {
			return(new ImageIcon(ImageIO.read(icon_stream)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public static StringPair WriteHtmlToFile(String html_content) {
		try {
	         final Path local_path = Files.createTempFile(Cval.PROJECT_NAME, ".html");
	         Files.write(local_path, html_content.getBytes());
	         //System.out.println("html_content :-->" + html_content);
		     local_path.toFile().deleteOnExit();
	         return(UploadFile(getOnlineFilename(".html") , local_path.toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public static String GetHtmlFromFile(String file_id) {
    	InputStream html_stream = DownloadFile(file_id);
    	
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		try {
			for (int length; (length = html_stream.read(buffer)) != -1; )
			    result.write(buffer, 0, length);
			//System.out.println("--->" + result.toString("UTF-8"));
			return(result.toString("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			return Cval.EMPTY_HTML;
		}
    }
    
	@SuppressWarnings("deprecation")
	public static void create_current_year_subfolder() {
    	String path = "/" + Calendar.getInstance().get(Calendar.YEAR);
    	try {
    		DbxClientV2 client=new DbxClientV2(new DbxRequestConfig(Cval.PROJECT_NAME), Cval.DROPBOX_ACCESS_TOKEN);
			client.files().createFolder(path);
			client.files().createFolder(path+"/"+"images");
			client.files().createFolder(path+"/"+"docs");
		} catch (CreateFolderErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	 public static String DownloadTxtFile(String file_id) {			
		try {
			DbxClientV2 client=new DbxClientV2(new DbxRequestConfig(Cval.PROJECT_NAME), Cval.DROPBOX_ACCESS_TOKEN);
			DbxDownloader<FileMetadata> dl;
			FileOutputStream fOut;
			dl = client.files().download("id:"+file_id);
			//dl = client.files().download("/test1.html");
			Path local_path = Files.createTempFile(Cval.PROJECT_NAME, ".html");
			//System.out.println(local_path);
			fOut = new FileOutputStream(local_path.toString());
			dl.download(new ProgressOutputStream(fOut, dl.getResult().getSize(), (long completed , long totalSize) -> {
				//System.out.println( ( completed * 100 ) / totalSize + " %");
			}));
			fOut.close();
			local_path.toFile().deleteOnExit();
			
			String ret_val = FileToString(local_path.toString());
			//System.out.println(ret_val);
			return ret_val;
		}catch (DownloadErrorException e) {
			e.printStackTrace();
		} catch (DbxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;								
	}
	 public static void OpenUrl(String url) {
	     if(Desktop.isDesktopSupported()){
	         try {
		        Desktop desktop = Desktop.getDesktop();
				desktop.browse(new URI(url));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
	     }else{
	         Runtime runtime = Runtime.getRuntime();
	         try {
	             runtime.exec("xdg-open " + url);
	         } catch (IOException e) {
	             // TODO Auto-generated catch block
	             e.printStackTrace();
	         }
	     }
	 }
	 
	 private static String getOnlineFilename(String file_type) {
		 String online_folder = file_type.endsWith("pdf") ? "docs" : "images";
		 String online_path = "/"+ Calendar.getInstance().get(Calendar.YEAR) + "/" + online_folder + "/";
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");  
		 LocalDateTime now = LocalDateTime.now();   
		 String online_filename = online_path + "msg_"+db_interface.user_id+"_"+ dtf.format(now).toString() + file_type;
		 return online_filename;
	 }
	 
	 public static String FileToString(String path){
		 String txt_file_contents=""; 
		 BufferedReader br=null;
		 try {
			 br = new BufferedReader(new FileReader(path));
		 
		     StringBuilder sb = new StringBuilder();
		     String line = br.readLine();
		     while (line != null) {
		         sb.append(line);
		         sb.append(System.lineSeparator());
		         line = br.readLine();
		     }
		     txt_file_contents = sb.toString();
		     br.close();
		 } catch (Exception e) {
			 e.printStackTrace();
		 } 
		 //System.out.println("====>" + txt_file_contents);
		 return txt_file_contents;
	 }
	    public static ImageIcon get_image_to_icon(String file_id) {
	    	DbxDownloader<FileMetadata> downloader=null;
	        try {
	        	DbxClientV2 client=new DbxClientV2(new DbxRequestConfig(Cval.PROJECT_NAME), Cval.DROPBOX_ACCESS_TOKEN);
	        	file_id ="id:" + file_id;
		       	downloader = client.files().download(file_id);
		        downloader.getResult();
	            return(new ImageIcon(ImageIO.read(downloader.getInputStream())));
            } catch (Exception e){
            	e.printStackTrace();
            } finally {
                downloader.close(); 
            }
	        return null;
	    }
	    
	    public static boolean delete_file(String file_id) {
	    	try {
	    		DbxClientV2 client=new DbxClientV2(new DbxRequestConfig(Cval.PROJECT_NAME), Cval.DROPBOX_ACCESS_TOKEN);
	    		file_id ="id:" + file_id;
	    		client.files().permanentlyDelete(file_id);
	    		return true;
            } catch (Exception e){
            	e.printStackTrace();
            	return false;
            }
		}	    
	    
		public static ImageIcon PickAPhotoFile() {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Images, png Images", "jpg", "png");
			chooser.setFileFilter(filter);
			while (true) {
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//System.out.println("You chose to open this file: " +  chooser.getSelectedFile().getName());
					BufferedImage img = null;
					try {
						LastSelectedImage = chooser.getSelectedFile().getAbsolutePath();//   getName();
					    img = ImageIO.read(new File(LastSelectedImage));
					    if (img.getHeight()<=256 && img.getWidth()<=256) return(new ImageIcon(img));
					} 
					catch (IOException e) {
					    e.printStackTrace();
					    return(null);
					}
				} else return null;
			}
		}
		
		public static String getHtmlText(InputStream fp) throws IOException {
			 ByteArrayOutputStream result = new ByteArrayOutputStream();
			 byte[] buffer = new byte[1024];
			 for (int length; (length = fp.read(buffer)) != -1; ) {
			     result.write(buffer, 0, length);
			 }
			 // StandardCharsets.UTF_8.name() > JDK 7
			 return(result.toString("UTF-8"));
		}
		
		public static String getHtmlText(URL url) throws IOException {
			InputStream fp = url.openStream();
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			for (int length; (length = fp.read(buffer)) != -1; ) {
			    result.write(buffer, 0, length);
			}
			// StandardCharsets.UTF_8.name() > JDK 7
			return(result.toString("UTF-8"));
		}
		
	    public static StringPair store_file_online(String filename, boolean image) {
	    	String path = "/"+ year + "/" + (image?"images":"docs") + "/";
	    	//System.out.println("----->" + path);
		    try  {
		    	DbxClientV2 client=new DbxClientV2(new DbxRequestConfig(Cval.PROJECT_NAME), Cval.DROPBOX_ACCESS_TOKEN);
		    	InputStream in = new FileInputStream(filename);
		    	String online_filename = path+filename.substring(filename.lastIndexOf("\\")+1);
		    	//System.out.println("===>>>" + online_filename);
		        FileMetadata metadata = client.files().uploadBuilder(online_filename)
		      		.withMode(WriteMode.OVERWRITE).uploadAndFinish(in);
		        	//System.out.println("0" + metadata.getPathDisplay());
		        	SharedLinkMetadata sharedLinkMetadata = client.sharing().createSharedLinkWithSettings(metadata.getPathDisplay());
		        	//String shared_link = sharedLinkMetadata.getUrl();
		        	//System.out.println("1" + shared_link);
		            //System.out.println(metadata.getId());
		            return(new StringPair(metadata.getId(), sharedLinkMetadata.getUrl()));
		    } catch (DbxException e) {
					e.printStackTrace();
					return null;
			} catch (IOException e) {
					e.printStackTrace();
					return null;
			}
        }

}
