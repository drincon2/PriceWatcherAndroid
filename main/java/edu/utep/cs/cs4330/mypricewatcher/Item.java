package edu.utep.cs.cs4330.mypricewatcher;

public class Item {
    private String name;
    private String initialPrice;
    private String currentPrice;
    private String changePrice;

    public Item(String name, String initialPrice, String currentPrice, String changePrice) {
        this.name = name;
        this.initialPrice = initialPrice;
        this.currentPrice = currentPrice;
        this.changePrice = changePrice;
    }

    //Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Initial price
    public String getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(String initialPrice) {
        this.initialPrice = initialPrice;
    }

    //Current Price
    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }


    //Change Price
    public String getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(String changePrice) {
        this.changePrice = changePrice;
    }

}
