package aura_game.app;

import java.util.ArrayList;
import java.util.List;

/**Différents type de bordure possible.
 * La direction fait référence à la direction vers laquelle se trouve l'herbe... de la texture
 */
public enum Border {

    //O: coins out (vers l'extérieur)
    //I: coins in (vers l'intérieur)
    ORIGHT_BOTTOM("out", "RIGHT","BOTTOM"),
    BOTTOM("BOTTOM"),
    OBOTTOM_LEFT("out", "BOTTOM","LEFT"),
    RIGHT("RIGHT"),
    NOBORDER("noborder"),
    LEFT("LEFT"),
    ORIGHT_TOP("out", "RIGHT","TOP"),
    TOP("TOP"),
    OTOP_LEFT("out", "TOP","LEFT"),

    ILEFT_TOP("in", "LEFT","TOP"),
    ITOP_RIGHT("in", "TOP","RIGHT"),
    ILEFT_BOTTOM("in", "LEFT","BOTTOM"),
    IBOTTOM_RIGHT("in", "BOTTOM","RIGHT");

    final String type;
    final String first;
    final String second;

    /**
     * Constructeur pour les bords doubles (coins)
     * @param type in ou out
     * @param first left, right, top, bottom
     * @param second left, right, top, bottom

     */
    Border(String type, String first, String second) {
        this.first = first;
        this.second = second;
        this.type = type;
    }

    /**
     * Constructeur pour les bords simples (top, right, left, bottom)
     * second sera null
     * le type sera Simple ou noborder
     * @param first top, right, left, bottom
     */
    Border(String first) {
        this.first = first;
        this.second = null;
        if(first.equals("noborder")){
            this.type = "noborder";
        }else{
            this.type = "simple";
        }
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    public Border getFirstAsBorder() {
        return Border.valueOf(first);
    }

    public Border getSecondAsBorder() {
        return Border.valueOf(second);
    }

    public String getType() {
        return type;
    }

    public boolean containsDirection(String direction){
        return this.first.equals(direction) || (this.second != null && this.second.equals(direction));
    }

}
