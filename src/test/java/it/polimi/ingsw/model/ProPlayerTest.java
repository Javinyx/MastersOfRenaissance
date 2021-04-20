package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.cards.leader.LeaderCard;
import it.polimi.ingsw.model.cards.leader.StorageAbility;
import it.polimi.ingsw.model.cards.production.ConcreteProductionCard;
import it.polimi.ingsw.model.market.Resource;
import it.polimi.ingsw.model.player.BadStorageException;
import it.polimi.ingsw.model.player.ProPlayer;
import it.polimi.ingsw.model.stub.PlayerStub;
import it.polimi.ingsw.model.stub.SingleGameStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProPlayerTest extends PlayerTest {
    ProPlayer p1, p2, p3;
    PlayerStub p;
    Game g, g1, g2;
    @BeforeEach
    public void testSetup(){
        g = new SingleGameStub();
        g1 = new SinglePlayerGame();
        g2 = new MultiplayerGame();
        p = new PlayerStub("Gatto", 1, g);
        p.registerObserver(g);
        p1 = new ProPlayer("Anacleto", 1, g1);
        p1.registerObserver(g1);
        p2 = new ProPlayer("Coco", 1, g2);
        p2.registerObserver((Observer)g2);
        p3 = new ProPlayer("Noob", 2, g2);
        p3.registerObserver(g2);
    }

    @Test
    void getTurnType() {
        p3.buyFromMarket('c',1, null);
        p1.buyFromMarket('c', 1, null);
        p2.buyFromMarket('r', 2, null);
        assertEquals('m', p1.getTurnType());
        assertEquals('m', p2.getTurnType());

        p2.storeInWarehouse(Resource.COIN, 1);
        p2.storeInWarehouse(Resource.SERVANT, 2);
        assertEquals('m', p2.getTurnType());

        //TEST "BUY PRODUCTION CARD"

    }

    @Test
    void getTurnID() {
        assertEquals(1, p2.getTurnID());
        assertEquals(2, p3.getTurnID());
        assertEquals(1, p1.getTurnID());
    }

    @Test
    void buyProductionCardWithExceptions() {
        ConcreteProductionCard productionCard = g.getBuyableProductionCards().get(0);
        ResourcesWallet wallet = new ResourcesWallet();

        assertThrows(BadStorageException.class, () -> p.buyProductionCard(productionCard, 3, null, wallet));
        wallet.setWarehouseTray(productionCard.getCost());
        assertEquals(g.getBuyableProductionCards().get(0), productionCard);
        assertThrows(IndexOutOfBoundsException.class, () -> p.buyProductionCard(productionCard, 4, null, wallet));
        assertThrows(BadStorageException.class, () -> p.buyProductionCard(productionCard, 2, null, wallet));
    }

    @Test
    void buyProductionCardWithLootchest(){
        ConcreteProductionCard productionCard = g.getBuyableProductionCards().get(1);
        p.setLootChest(productionCard.getCost());
        ResourcesWallet wallet = new ResourcesWallet();
        wallet.setLootchestTray(productionCard.getCost());
        try {
            p.buyProductionCard(productionCard, 1, null, wallet);
        }catch(BadStorageException e){
            e.printStackTrace();
        }
        assertEquals(productionCard, p.getProdCards1().peekFirst());
        assertEquals(0, p.getLootChest().getInventory().get(Resource.COIN));
        assertEquals(0, p.getLootChest().getInventory().get(Resource.SERVANT));
        assertEquals(0, p.getLootChest().getInventory().get(Resource.SHIELD));
        assertEquals(0, p.getLootChest().getInventory().get(Resource.STONE));
        assertEquals('b', p.getTurnType());

        ConcreteProductionCard productionCard1 = g.getBuyableProductionCards().get(0);
        List<Resource> res = new ArrayList<>();
        res.addAll(productionCard1.getCost());
        res.add(Resource.STONE); //one res in surplus
        p.setLootChest(res);
        ResourcesWallet wallet1 = new ResourcesWallet();
        wallet1.setLootchestTray(productionCard1.getCost());
        try {
            p.buyProductionCard(productionCard1, 2, null, wallet1);
        } catch (BadStorageException e) {
            e.printStackTrace();
        }
        assertEquals(productionCard1, p.getProdCards2().peekFirst());
        assertEquals(0, p.getLootChest().getInventory().get(Resource.COIN));
        assertEquals(0, p.getLootChest().getInventory().get(Resource.SERVANT));
        assertEquals(0, p.getLootChest().getInventory().get(Resource.SHIELD));
        assertEquals(1, p.getLootChest().getInventory().get(Resource.STONE));
    }

    @Test
    void buyProductionCardWithWarehouse(){
        ConcreteProductionCard productionCard = g.getBuyableProductionCards().get(1);
        ResourcesWallet wallet = new ResourcesWallet();
        p.initWarehouse(productionCard.getCost());
        wallet.setWarehouseTray(productionCard.getCost());

        try {
            p.buyProductionCard(productionCard, 3, null, wallet);
        } catch (BadStorageException e) {
            e.printStackTrace();
        }
        assertEquals(productionCard, p.getProdCards3().peekFirst());
        assertNull(p.getWarehouse().getSmallInventory());
        assertTrue(p.getWarehouse().getMidInventory().isEmpty());
        assertTrue(p.getWarehouse().getLargeInventory().isEmpty());
    }

    /*private ConcreteProductionCard generateFirstProdCard(){
        List<Resource> cost = new ArrayList<>();
        List<Resource> requiredRes = new ArrayList<>();
        List<Resource> prod = new ArrayList<>();
        cost.add(Resource.SHIELD);
        cost.add(Resource.SHIELD);
        requiredRes.add(Resource.COIN);
        prod.add(Resource.FAITH);

        return new ConcreteProductionCard(1, 1, ColorEnum.GREEN, 1, cost, requiredRes, prod);
    }

    private boolean fillWarehouse(ConcreteProductionCard card, ProPlayer p){
        switch(card.getId()){
            case 1: p.storeInWarehouse(Resource.SHIELD, 2);
                p.storeInWarehouse(Resource.SHIELD, 2);
                return true;
            case 5: p.storeInWarehouse(Resource.SHIELD,1);
                p.storeInWarehouse(Resource.SERVANT, 2);
                p.storeInWarehouse(Resource.COIN,3);
                return true;
            case 9: p.storeInWarehouse(Resource.SHIELD,3);
                p.storeInWarehouse(Resource.SHIELD,3);
                p.storeInWarehouse(Resource.SHIELD, 3);
                return true;
            default: return false;
        }
    }*/

    @Test
    void getVictoryPoints() {
        p1.addFaithPoints(24);
        assertEquals(20, p1.getVictoryPoints());
    }

    @Test
    void getResAcquired() {

    }

    @Test
    void getWarehouse() {
        ArrayList<Resource> a = new ArrayList();

        assertNull(p1.getWarehouse().getSmallInventory());
        assertEquals(a, p1.getWarehouse().getMidInventory());
        assertEquals(a, p2.getWarehouse().getLargeInventory());
        warehouseSetup(p1);
        assertNotEquals(a, p1.getWarehouse().getMidInventory());
        p.fullWarehouseInit();
        assertEquals(Resource.COIN, p.getWarehouse().getSmallInventory());
        assertEquals(2, p.getWarehouse().getMidInventory().size());
        assertEquals(3, p.getWarehouse().getLargeInventory().size());
        assertEquals(Resource.SERVANT, p.getWarehouse().getMidInventory().get(0));
        assertEquals(Resource.SERVANT, p.getWarehouse().getMidInventory().get(1));
        assertEquals(Resource.SHIELD, p.getWarehouse().getLargeInventory().get(0));
    }

    void warehouseSetup(ProPlayer p){
        p.storeInWarehouse(Resource.SERVANT, 1);
        p.storeInWarehouse(Resource.COIN, 2);
        p.storeInWarehouse(Resource.COIN, 2);
        p.storeInWarehouse(Resource.SHIELD, 3);
    }

    @Test
    void getLootChest() {
        ArrayList<Resource> res = new ArrayList<>();
        assertEquals(4, p.getLootChest().getInventory().size());
        assertEquals(0, p.getLootChest().getInventory().get(Resource.COIN));
        res.add(Resource.STONE);
        res.add(Resource.COIN);
        res.add(Resource.COIN);
        p.setLootChest(res);
        assertEquals(1, p.getLootChest().getInventory().get(Resource.STONE));
        assertEquals(2, p.getLootChest().getInventory().get(Resource.COIN));
    }

    @Test
    void buyFromMarket() {

    }

    @Test
    void discardResources() {
    }

    @Test
    void chooseLeaders(){
        assertThrows(IllegalArgumentException.class, () -> p.chooseLeaders(null));
        List<LeaderCard> leaderCards = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> p.chooseLeaders(leaderCards));
        leaderCards.add((LeaderCard) g.getLeaderDeckNew().getFirst());
        leaderCards.add((LeaderCard) g.getLeaderDeckNew().getFirst());
        assertEquals(2,(int)leaderCards.stream().distinct().count());
        p.chooseLeaders(leaderCards);
        assertEquals(2, p.getLeaderCards().size());
        assertEquals(leaderCards.get(0), p.getLeaderCards().get(0));
        assertEquals(leaderCards.get(1), p.getLeaderCards().get(1));
        assertFalse(p.getLeaderCards().get(0).isActive());
    }

    @Test
    void activateLeaderCard() {

    }

    @Test
    void discardLeaderCard() {
        LeaderCard leaderCard = (LeaderCard) g.leaderDeck.getFirst();
        List<LeaderCard> playerList = p.getLeaderCards();
        p.discardLeaderCard(leaderCard);
        assertEquals(playerList, p.getLeaderCards());
        playerList = new ArrayList<>();
        playerList.add(leaderCard);
        playerList.add((LeaderCard) g.leaderDeck.getFirst());
        p.chooseLeaders(playerList);
        p.activateLeader(leaderCard);
        assertThrows(RuntimeException.class, () -> p.discardLeaderCard(leaderCard));
        p.disableLeader(leaderCard);
        p.discardLeaderCard(leaderCard);
        playerList.remove(leaderCard);
        assertEquals(playerList.size(), p.getLeaderCards().size());
        assertEquals(playerList, p.getLeaderCards());
    }

    @Test
    void chooseResource() {
    }

    @Test
    void storeInWarehouse() {
        assertFalse(p1.storeInWarehouse(null, 1));
        assertTrue(p1.storeInWarehouse(Resource.SHIELD, 1));
        assertEquals(Resource.SHIELD, p1.getWarehouse().getSmallInventory());
        assertFalse(p1.storeInWarehouse(Resource.BLANK,2));
        assertEquals(0, p1.getWarehouse().getMidInventory().size());
        assertTrue(p1.storeInWarehouse(Resource.COIN, 2));

        //different resources on the same shelf
        assertFalse(p1.storeInWarehouse(Resource.SERVANT, 2));
        assertEquals(1, p1.getWarehouse().getMidInventory().size());
        assertEquals(Resource.COIN, p1.getWarehouse().getMidInventory().get(0));

        //same type on different shelves
        assertFalse(p1.storeInWarehouse(Resource.SHIELD, 3));
        assertEquals(0, p1.getWarehouse().getLargeInventory().size());

        assertFalse(p1.storeInWarehouse(Resource.SERVANT,4));
    }

    @Test
    void isInRangeForReport() {
        assertFalse(p1.isInRangeForReport(1));
        assertFalse(p1.isInRangeForReport(2));
        assertFalse(p1.isInRangeForReport(3));
        p1.addFaithPoints(5);
        assertTrue(p1.isInRangeForReport(1));
        assertFalse(p1.isInRangeForReport(2));
        p1.addFaithPoints(8);
        assertTrue(p1.isInRangeForReport(1));
        assertTrue(p1.isInRangeForReport(2));
        assertFalse(p1.isInRangeForReport(3));
        p1.addFaithPoints(7);
        assertTrue(p1.isInRangeForReport(1));
        assertTrue(p1.isInRangeForReport(2));
        assertTrue(p1.isInRangeForReport(3));
    }

    @Disabled
    void startProduction() {
        List<Deck> prodDecks = Deck.createProdDeckList();
        ConcreteProductionCard productionCard = (ConcreteProductionCard) prodDecks.get(0).getFirst();
        ResourcesWallet wallet = new ResourcesWallet();
        List<ConcreteProductionCard> cards = new ArrayList<>();
        cards.add(productionCard);
        p1.storeInWarehouse(Resource.COIN, 1);
        //p1.buyProductionCard(productionCard, 1, null, wallet);

        assertThrows(RuntimeException.class, () -> p1.startProduction(cards, wallet, null, null, false, null));

        List<Resource> resFromLoot = new ArrayList<>();
        resFromLoot.add(Resource.COIN);
        wallet.setLootchestTray(resFromLoot);
    }


    @Disabled
    void setExtraStorage() {
        List<Resource> cardCost = new ArrayList<>();
        cardCost.add(Resource.STONE);
        StorageAbility storageCard1 = new StorageAbility(1, 1, cardCost, Resource.SERVANT);
        cardCost.add(Resource.COIN);
        StorageAbility storageCard2 = new StorageAbility(2, 1, cardCost, Resource.SHIELD);
        StorageAbility storageCard3 = new StorageAbility(3, 1, cardCost, Resource.COIN);

        /*assertNull(p1.getExtraStorage1());
        assertNull(p1.getExtraStorage2());
        p1.setExtraStorage(storageCard1);
        assertEquals(storageCard1, p1.getExtraStorage1());
        assertNull(p1.getExtraStorage2());

        p1.setExtraStorage(storageCard2);
        assertEquals(storageCard1, p1.getExtraStorage1());
        assertEquals(storageCard2, p1.getExtraStorage2());
        assertThrows(RuntimeException.class, () -> p1.setExtraStorage(storageCard3));*/

        assertDoesNotThrow(()-> p1.setExtraStorage(storageCard1));
    }

    @Test
    void drawActionToken(){

    }

    /* private ConcreteProductionCard istantiateACard(){
        List<Resource> required = new ArrayList<>();
        required.add(Resource.STONE);
        List<Resource> cost = new ArrayList<>();
        cost.add(Resource.COIN);
        List<Resource> prod = new ArrayList<>();
        prod.add(Resource.SERVANT);
        return new ConcreteProductionCard(ColorEnum.GREEN, required, prod, cost, 1, 1);
    }*/
}