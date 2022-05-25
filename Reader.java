package reader_app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;

public class Reader {
	private static int zoomCount = 1;
	private static Text zoomMsg = new Text("x" + zoomCount);
	private static ImageView iv = new ImageView();
	private static Button nextPage = new Button();
	private static Button prevPage = new Button();
	private static Button zoomPlus = new Button();
	private static Button zoomMinus = new Button();
	private static BorderPane bpane = new BorderPane();
	private static VBox vBox = new VBox();
	private static int pageCount;
	private static PDDocument document = new PDDocument();
	private static PDFRenderer pdfRenderer = new PDFRenderer(document);
	private static TextField tf = new TextField();
	public static Scene readerScene;
	
	public void startReader() {
	    int screenW = (int) Screen.getPrimary().getBounds().getWidth();
		int screenH = (int) Screen.getPrimary().getBounds().getHeight();
		nextPage.setOnAction(e -> loadNext());
		prevPage.setOnAction(e -> loadPrev());
		zoomPlus.setOnAction(e -> zoomIn());
		zoomMinus.setOnAction(e -> zoomOut());
		tf.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				try {
					int num = Integer.parseInt(tf.getText());
					if (num != pageCount + 1) {
						loadCustom(num);
					}
				} catch (Exception ex) {
					tf.setText("" + (pageCount + 1));
				}
			}
		});
		iv.setFitHeight(1000);
		iv.setFitWidth(1000);
		iv.setPreserveRatio(true);
		HBox hBox = new HBox(10);
		ScrollPane spane = new ScrollPane();
		vBox.getChildren().add(iv);
		vBox.setPadding(new Insets(10, 250, 10, 250));
		spane.setContent(vBox);
		tf.setMaxWidth(65);
		tf.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
		StackPane zpane = new StackPane();
		zoomMsg.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
		zoomMsg.setFill(Color.WHITE);
		zpane.getChildren().add(zoomMsg);
		zoomMinus.setGraphic(new ImageView("resources\\zoom_minus.png"));
		zoomPlus.setGraphic(new ImageView("resources\\zoom_plus.png"));
		nextPage.setGraphic(new ImageView("resources\\arrow_next.png"));
		prevPage.setGraphic(new ImageView("resources\\arrow_back.png"));
		hBox.getChildren().addAll(zoomMinus, zpane, zoomPlus, new Separator(Orientation.VERTICAL), prevPage, tf, nextPage);
		zoomMinus.setDisable(true);
		hBox.setAlignment(Pos.CENTER);
		spane.setMinViewportWidth(1000);
		spane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		spane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		bpane.setCenter(spane);
		bpane.setTop(hBox);
		Scene readerScene = new Scene(bpane, screenW, screenH);
		readerScene.getStylesheets().add(getClass().getResource("styles.css").toString());
		readerScene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ADD || e.getCode() == KeyCode.PLUS) {
				zoomIn();
			} else if (e.getCode() == KeyCode.SUBTRACT || e.getCode() == KeyCode.MINUS) {
				zoomOut();
			} else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.KP_LEFT) {
				loadPrev();
			} else if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.KP_RIGHT) {
				loadNext();
			} else if (e.getCode() == KeyCode.Q) {
				try {
					document.close();
				} catch (IOException io) {
					MainWindow.logger.warning("IOException: " + io.getMessage());
				}
				MainWindow.secondStage.hide();
				MainWindow.secondStage = null;
				MainWindow.primaryStage.show();
			} else if (e.getCode() == KeyCode.F) {
				MainWindow.secondStage.setFullScreen(true);
			}
		});
		Reader.readerScene = readerScene;
	}
	
	/**
	 * 
	 */
	public static void loadFile(File pdfFilename) {
		try {
			pageCount = -1;
			document = PDDocument.load(pdfFilename);
			pdfRenderer = new PDFRenderer(document);
			loadNext();
			prevPage.setDisable(true);
		} catch (IOException e) {
			MainWindow.logger.warning("IOException: " + e.getMessage());
		} catch (Exception e) {
			MainWindow.logger.warning("Exception: " + e.getMessage());
		}
	}

	/**
	 * 
	 */
	public static void loadNext() {
		try {
			if (pageCount < document.getNumberOfPages()) {
				pageCount++;
				BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCount, 300, ImageType.RGB);
				Image img = SwingFXUtils.toFXImage(bim, null);
				iv.setImage(img);
				tf.setText("" + (pageCount + 1));
				prevPage.setDisable(false);
				if (pageCount == document.getNumberOfPages()) {
					nextPage.setDisable(true);
				}
				bpane.requestFocus();
			}
		} catch (Exception e) {
			MainWindow.logger.warning("Exception: " + e.getMessage());
		}
	}

	/**
	 * 
	 */
	public static void loadPrev() {
		try {
			if (pageCount > 0) {
				pageCount--;
				BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCount, 300, ImageType.RGB);
				Image img = SwingFXUtils.toFXImage(bim, null);
				iv.setImage(img);
				tf.setText("" + (pageCount + 1));
				nextPage.setDisable(false);
				if (pageCount == 0) {
					prevPage.setDisable(true);
				}
				bpane.requestFocus();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param i
	 */
	public static void loadCustom(int i) {
		try {
			int currentPage = pageCount;
			if (i >= 0 & i <= document.getNumberOfPages()) {
				if (i > currentPage) {
					pageCount = i - 2;
					loadNext();
				} else {
					pageCount = i;
					loadPrev();
				}
			} else {
				tf.setText("" + (pageCount + 1));
			}
		} catch (Exception e) {
			MainWindow.logger.warning("Exception: " + e.getMessage());
		}
	}

	/**
	 * 
	 */
	public static void zoomIn() {
		try {
			if (zoomCount <= 16) {
				zoomCount *= 2;
				switch (zoomCount) {
				case 2:
					vBox.setPadding(new Insets(10, 225, 10, 225));
					iv.setFitHeight(1100);
					iv.setFitWidth(1100);
					zoomMsg.setText("x" + zoomCount);
					zoomMinus.setDisable(false);
					break;
				case 4:
					vBox.setPadding(new Insets(10, 200, 10, 200));
					iv.setFitHeight(1300);
					iv.setFitWidth(1300);
					zoomMsg.setText("x" + zoomCount);
					break;
				case 8:
					vBox.setPadding(new Insets(10, 175, 10, 175));
					iv.setFitHeight(1500);
					iv.setFitWidth(1500);
					zoomMsg.setText("x" + zoomCount);
					break;
				case 16:
					vBox.setPadding(new Insets(8, 150, 8, 150));
					iv.setFitHeight(1800);
					iv.setFitWidth(1800);
					zoomMsg.setText("x" + zoomCount);
					zoomPlus.setDisable(true);
					break;
				}
			}
		} catch (Exception e) {
			MainWindow.logger.warning("Exception: " + e.getMessage());
		}
	}

	/**
	 * 
	 */
	public static void zoomOut() {
		try {
			if (zoomCount > 1) {
				switch (zoomCount) {
				case 2:
					vBox.setPadding(new Insets(10, 250, 10, 250));
					iv.setFitHeight(1000);
					iv.setFitWidth(1000);
					zoomMsg.setText("x" + zoomCount / 2);
					zoomMinus.setDisable(true);
					break;
				case 4:
					vBox.setPadding(new Insets(10, 225, 10, 225));
					iv.setFitHeight(1100);
					iv.setFitWidth(1100);
					zoomMsg.setText("x" + zoomCount / 2);
					break;
				case 8:
					vBox.setPadding(new Insets(10, 200, 10, 200));
					iv.setFitHeight(1300);
					iv.setFitWidth(1300);
					zoomMsg.setText("x" + zoomCount / 2);
					break;
				case 16:
					vBox.setPadding(new Insets(10, 175, 10, 175));
					iv.setFitHeight(1500);
					iv.setFitWidth(1500);
					zoomMsg.setText("x" + zoomCount / 2);
					zoomPlus.setDisable(false);
					break;
				}
				zoomCount /= 2;
			}
		} catch (Exception e) {
			MainWindow.logger.warning("Exception: " + e.getMessage());
		}
	}
}