package it.polimi.ingsw.messages.concreteMessages;

import it.polimi.ingsw.messages.SimpleMessage;
import it.polimi.ingsw.model.player.ProPlayer;

public class ActivateLeaderMessage extends SimpleMessage {
    private final int leaderId;

    public ActivateLeaderMessage(int leaderId){
        this.leaderId = leaderId;
    }

    public int getLeaderId(){
        return leaderId;
    }
}
