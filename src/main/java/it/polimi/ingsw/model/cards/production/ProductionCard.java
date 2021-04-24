package it.polimi.ingsw.model.cards.production;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.market.Buyable;

/**
 * The abstraction of a ConcreteProductionCard.
 */
public abstract class ProductionCard implements Buyable, Card {
    /**
     * The Color.
     */
    protected ColorEnum color;
    /**
     * The Level.
     */
    protected int level;

    /**
     * Get color color enum.
     *
     * @return the color enum
     */
    public ColorEnum getColor(){return color;}

    /**
     * Get level int.
     *
     * @return the int
     */
    public int getLevel(){return level;}

    public boolean isOfType(Buyable res){
        if(res instanceof ProductionCard && this.level==((ProductionCard) res).level && (this.color).equals(((ProductionCard) res).color)){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "Color: " + color + ", level: " + level;
    }

    public boolean isEquivalent(Buyable other){
        if(other instanceof ProductionCard || other instanceof ConcreteProductionCard){
            if(((ProductionCard) other).level==this.level && ((ProductionCard) other).color==this.color){
                return true;
            }
        }
        return false;
    }
}
