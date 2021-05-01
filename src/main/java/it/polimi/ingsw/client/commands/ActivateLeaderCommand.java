package it.polimi.ingsw.client.commands;

import com.google.gson.Gson;
import it.polimi.ingsw.client.MessageToServerHandler;
import it.polimi.ingsw.client.ViewInterface;
import it.polimi.ingsw.messages.ActivateLeaderMessage;
import it.polimi.ingsw.messages.MessageEnvelope;
import it.polimi.ingsw.messages.MessageID;
import it.polimi.ingsw.messages.SimpleMessage;

public class ActivateLeaderCommand extends MessageToServerHandler {
    private final ViewInterface view;
    private final Gson gson;

    public ActivateLeaderCommand(ViewInterface view, Gson gson){
        this.view = view;
        this.gson = gson;
    }

    @Override
    public void generateEnvelope(SimpleMessage message){
        String payload = gson.toJson(message, ActivateLeaderMessage.class);
        MessageEnvelope envelope = new MessageEnvelope(MessageID.ACTIVATE_LEADER, payload);
    }

}
