package com.vpigadas.chessgame.utils;

/**
 * Created by vpigadas on 28/05/2017.
 */

public class Solution {

    private int positionOne = -1;
    private int positionTwo = -1;
    private int positionThree = -1;

    public Solution(int pos) {
        this.positionOne = pos;
    }

    public void setPositionTwo(int positionTwo) {
        this.positionTwo = positionTwo;
    }

    public void setPositionThree(int positionThree) {
        this.positionThree = positionThree;
    }

    public int getPositionOne() {
        return positionOne;
    }

    public int getPositionTwo() {
        return positionTwo;
    }

    public int getPositionThree() {
        return positionThree;
    }

    private String getPosition(int position) {
        int axeX = position / 8;
        String strX = String.valueOf(axeX + 1);

        int axeY = position % 8;
        String strY = "A";
        switch (axeY) {
            case 1: {
                strY = "B";
            }
            break;
            case 2: {
                strY = "C";
            }
            break;
            case 3: {
                strY = "D";
            }
            break;
            case 4: {
                strY = "E";
            }
            break;
            case 5: {
                strY = "F";
            }
            break;
            case 6: {
                strY = "G";
            }
            break;
            case 7: {
                strY = "J";
            }
            break;
        }

        return String.format("%s%s", strY, strX);
    }

    @Override
    public String toString() {
        String result = "Solution{" + "move->" + getPosition(positionOne);

        if (positionTwo > -1)
            result = result + ", move->" + getPosition(positionTwo);
        if (positionThree > -1)
            result = result + ", move->" + getPosition(positionThree);

        return result + '}';
    }
}
