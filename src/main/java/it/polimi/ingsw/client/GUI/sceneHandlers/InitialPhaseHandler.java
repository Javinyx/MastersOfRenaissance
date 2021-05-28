package it.polimi.ingsw.client.GUI.sceneHandlers;

import it.polimi.ingsw.client.GUI.GuiController;
import it.polimi.ingsw.misc.BiElement;
import it.polimi.ingsw.model.cards.leader.LeaderCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static it.polimi.ingsw.client.GUI.sceneHandlers.ScenesEnum.*;

/**
 * This class is the handler for the welcome, connection, registration, waiting room, leaders choice and resource choice
 * action during the initial phase.
 */
public class InitialPhaseHandler extends PhaseHandler {
    private String ip, port;
    private String nickname, gameSize;
    private int ctr;

    private Map<ScenesEnum, Scene> sceneMap = new HashMap<>();
    //private Map<ScenesEnum, InitialPhaseHandler> sceneControllerMap = new HashMap<>();

    @FXML
    private Button playBtn, quitBtn, connectBtn, localModeBtn;
    @FXML
    private TextField ipField, portField, nickNameField;
    @FXML
    private Button singlePlayerBtn, twoPlayerBtn, threePlayerBtn, fourPlayerBtn;
    @FXML
    private Label waitingRoomLbl;
    @FXML
    private Button chooseLeadersBtn, chooseResourcesBtn;
    @FXML
    private ToggleButton leader1Toggle, leader2Toggle, leader3Toggle, leader4Toggle;
    @FXML
    private ImageView leader1Img, leader2Img, leader3Img, leader4Img;
    @FXML
    private Label chooseResLbl, chooseFaithLbl;
    @FXML
    private Button stoneSubBtn, stoneAddBtn, servantSubBtn, servantAddBtn, coinSubBtn, coinAddBtn, shieldSubBtn, shieldAddBtn;
    @FXML
    private Label stoneLbl, servantLbl, coinLbl, shieldLbl, resPluralLbl;

    public InitialPhaseHandler(GuiController controller, Stage stage) {
        super(controller, stage);

        List<ScenesEnum> allPaths = new ArrayList<>(Arrays.asList(WELCOME, CONNECTION, REGISTRATION, WAITING_ROOM, CHOOSE_LEADERS, CHOOSE_RESOURCES, MAIN_BOARD));
        for (ScenesEnum path : allPaths) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path.getPath()));
            loader.setController(this);

            try {
                Scene scene = new Scene(loader.load());

                scene.getStylesheets().addAll(this.getClass().getResource("/fxml/style.css").toExternalForm());
                scene.setCursor(new ImageCursor(new Image("/img/ui/cursor.png"), 5, 5));

                sceneMap.put(path, scene);

            } catch (IOException e) {
                System.err.println("Loader cannot load " + path);
            }
        }

        buildGeneralSceneMap(sceneMap);
    }

    public boolean setScene(ScenesEnum sceneName) {
        if (!sceneMap.containsKey(sceneName)) {
            return false;
        }
        stage.setScene(sceneMap.get(sceneName));
        return true;
    }

    @FXML
    public void start() {
        playBtn.setOnAction(actionEvent -> {
            try {
                controller.setup();
            } catch (IOException e) {
                System.err.println("IOException");
            }
        });

        quitBtn.setOnAction(actionEvent -> {
            System.exit(0);
        });
    }

    /**
     * @return a {@link BiElement} containing the IP as first value and the Port as second.
     */
    @FXML
    public void retrieveIpAndPort() {
        connectBtn.setOnAction(actionEvent -> {
            if (ipField.getText().length() > 0 && ipField.getText().length() > 0) {
                ip = ipField.getText();
                port = portField.getText();
            } else {
                ip = "localhost";
                port = "27001";
            }
            controller.setIpAndPort(ip, port);
        });

        localModeBtn.setOnAction(actionEvent -> {
            ip = "0";
            port = "0";
            controller.setIpAndPort(ip, port);
        });
    }

    @FXML
    public void getNickNameAndGameSize() {
        //TODO: check nickname length
        singlePlayerBtn.setOnAction(actionEvent -> {
            nickname = nickNameField.getText();
            gameSize = singlePlayerBtn.getText();
            controller.setNickname(nickname);
            controller.setGameSize(gameSize);
        });

        twoPlayerBtn.setOnAction(actionEvent -> {
            nickname = nickNameField.getText();
            gameSize = twoPlayerBtn.getText();
            controller.setNickname(nickname);
            controller.setGameSize(gameSize);
        });

        threePlayerBtn.setOnAction(actionEvent -> {
            nickname = nickNameField.getText();
            gameSize = threePlayerBtn.getText();
            controller.setNickname(nickname);
            controller.setGameSize(gameSize);
        });

        fourPlayerBtn.setOnAction(actionEvent -> {
            nickname = nickNameField.getText();
            gameSize = fourPlayerBtn.getText();
            controller.setNickname(nickname);
            controller.setGameSize(gameSize);
        });
    }

    @FXML
    public void setWaitingRoomName(String nickName) {
        waitingRoomLbl.setText("Hello, " + nickName);
    }

    @FXML
    public void displayLeaders(List<LeaderCard> availableLeaders) {
        leader1Img.setImage(new Image("img/leaderCards/" + availableLeaders.get(0).getId() + ".png"));
        leader2Img.setImage(new Image("img/leaderCards/" + availableLeaders.get(1).getId() + ".png"));
        leader3Img.setImage(new Image("img/leaderCards/" + availableLeaders.get(2).getId() + ".png"));
        leader4Img.setImage(new Image("img/leaderCards/" + availableLeaders.get(3).getId() + ".png"));
    }

    @FXML
    public void chooseLeaders() {
        leader1Toggle.setOnAction(actionEvent -> {
            if (leader1Toggle.isSelected()) {
                if (ctr == 0) {
                    ctr++;
                } else if (ctr == 1) {
                    ctr++;
                    if (!leader2Toggle.isSelected()) {
                        leader2Toggle.setDisable(true);
                    }
                    if (!leader3Toggle.isSelected()) {
                        leader3Toggle.setDisable(true);
                    }
                    if (!leader4Toggle.isSelected()) {
                        leader4Toggle.setDisable(true);
                    }
                }
            } else {
                ctr--;
                if (leader2Toggle.isDisabled()) {
                    leader2Toggle.setDisable(false);
                }
                if (leader3Toggle.isDisabled()) {
                    leader3Toggle.setDisable(false);
                }
                if (leader4Toggle.isDisabled()) {
                    leader4Toggle.setDisable(false);
                }
            }
        });

        leader2Toggle.setOnAction(actionEvent -> {
            if (leader2Toggle.isSelected()) {
                if (ctr == 0) {
                    ctr++;
                } else if (ctr == 1) {
                    ctr++;
                    if (!leader1Toggle.isSelected()) {
                        leader1Toggle.setDisable(true);
                    }
                    if (!leader3Toggle.isSelected()) {
                        leader3Toggle.setDisable(true);
                    }
                    if (!leader4Toggle.isSelected()) {
                        leader4Toggle.setDisable(true);
                    }
                }
            } else {
                ctr--;
                if (leader1Toggle.isDisabled()) {
                    leader1Toggle.setDisable(false);
                }
                if (leader3Toggle.isDisabled()) {
                    leader3Toggle.setDisable(false);
                }
                if (leader4Toggle.isDisabled()) {
                    leader4Toggle.setDisable(false);
                }
            }
        });

        leader3Toggle.setOnAction(actionEvent -> {
            if (leader3Toggle.isSelected()) {
                if (ctr == 0) {
                    ctr++;
                } else if (ctr == 1) {
                    ctr++;
                    if (!leader2Toggle.isSelected()) {
                        leader2Toggle.setDisable(true);
                    }
                    if (!leader1Toggle.isSelected()) {
                        leader1Toggle.setDisable(true);
                    }
                    if (!leader4Toggle.isSelected()) {
                        leader4Toggle.setDisable(true);
                    }
                }
            } else {
                ctr--;
                if (leader2Toggle.isDisabled()) {
                    leader2Toggle.setDisable(false);
                }
                if (leader1Toggle.isDisabled()) {
                    leader1Toggle.setDisable(false);
                }
                if (leader4Toggle.isDisabled()) {
                    leader4Toggle.setDisable(false);
                }
            }
        });

        leader4Toggle.setOnAction(actionEvent -> {
            if (leader4Toggle.isSelected()) {
                if (ctr == 0) {
                    ctr++;
                } else if (ctr == 1) {
                    ctr++;
                    if (!leader2Toggle.isSelected()) {
                        leader2Toggle.setDisable(true);
                    }
                    if (!leader3Toggle.isSelected()) {
                        leader3Toggle.setDisable(true);
                    }
                    if (!leader1Toggle.isSelected()) {
                        leader1Toggle.setDisable(true);
                    }
                }
            } else {
                ctr--;
                if (leader2Toggle.isDisabled()) {
                    leader2Toggle.setDisable(false);
                }
                if (leader3Toggle.isDisabled()) {
                    leader3Toggle.setDisable(false);
                }
                if (leader1Toggle.isDisabled()) {
                    leader1Toggle.setDisable(false);
                }
            }
        });

        chooseLeadersBtn.setOnAction(actionEvent -> {
            //TODO: check if 2 leaders have been selected
            List<Boolean> selectedLeaders = new ArrayList<>();
            selectedLeaders.add(leader1Toggle.isSelected());
            selectedLeaders.add(leader2Toggle.isSelected());
            selectedLeaders.add(leader3Toggle.isSelected());
            selectedLeaders.add(leader4Toggle.isSelected());
            controller.setSelectedLeaders(selectedLeaders);
        });
    }

    @FXML
    public void displayResources(Integer resources) {
        chooseResLbl.setText(resources.toString());
        if (resources == 1) {
            resPluralLbl.setText("resource");
        } else {
            resPluralLbl.setText("resources");
        }

        if(controller.getPlayer().getTurnNumber() == 3 || controller.getPlayer().getTurnNumber() == 4){
            chooseFaithLbl.setText("You have also received 1 bonus faith point");
        } else {
            chooseFaithLbl.setDisable(true);
        }

        ctr = 0;

        stoneAddBtn.setOnAction(actionEvent -> {
            if (ctr < resources) {
                stoneLbl.setText(String.valueOf((Integer.parseInt(stoneLbl.getText()) + 1)));
                ctr++;
            }
        });
        stoneSubBtn.setOnAction(actionEvent -> {
            if (Integer.parseInt(stoneLbl.getText()) != 0) {
                stoneLbl.setText(String.valueOf((Integer.parseInt(stoneLbl.getText()) - 1)));
                ctr--;
            }
        });
        servantAddBtn.setOnAction(actionEvent -> {
            if (ctr < resources) {
                servantLbl.setText(String.valueOf((Integer.parseInt(servantLbl.getText()) + 1)));
                ctr++;
            }
        });
        servantSubBtn.setOnAction(actionEvent -> {
            if (Integer.parseInt(servantLbl.getText()) != 0) {
                servantLbl.setText(String.valueOf((Integer.parseInt(servantLbl.getText()) - 1)));
                ctr--;
            }
        });
        coinAddBtn.setOnAction(actionEvent -> {
            if (ctr < resources) {
                coinLbl.setText(String.valueOf((Integer.parseInt(coinLbl.getText()) + 1)));
                ctr++;
            }
        });
        coinSubBtn.setOnAction(actionEvent -> {
            if (Integer.parseInt(coinLbl.getText()) != 0) {
                coinLbl.setText(String.valueOf((Integer.parseInt(coinLbl.getText()) - 1)));
                ctr--;
            }
        });
        shieldAddBtn.setOnAction(actionEvent -> {
            if (ctr < resources) {
                shieldLbl.setText(String.valueOf((Integer.parseInt(shieldLbl.getText()) + 1)));
                ctr++;
            }
        });
        shieldSubBtn.setOnAction(actionEvent -> {
            if (Integer.parseInt(shieldLbl.getText()) != 0) {
                shieldLbl.setText(String.valueOf((Integer.parseInt(shieldLbl.getText()) - 1)));
                ctr--;
            }
        });

        chooseResourcesBtn.setOnAction(actionEvent -> {
            //TODO: get all the label texts, check if they're not 0 and ask where to store the resources
        });
    }

}