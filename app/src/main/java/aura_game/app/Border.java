package aura_game.app;

/**Différents type de bordure possible.
 * La direction fait référence à la direction vers laquelle se trouve l'herbe... de la texture
 */
public enum Border {
    //O: coins out (vers l'extérieur)
    //I: coins in (vers l'intérieur)
    ORIGHT_BOTTOM, BOTTOM, OBOTTOM_LEFT,
    RIGHT, NOBORDER, LEFT,
    ORIGHT_TOP, TOP, OTOP_LEFT,

    ILEFT_TOP, ITOP_RIGHT,
    ILEFT_BOTTOM, IBOTTOM_RIGHT;

    /**@return true si c'est un bord simple (top, right, left, bottom)*/
    public boolean isSimpleBorder(){
        return (this.equals(Border.TOP) ||this.equals(Border.RIGHT)||this.equals(Border.LEFT)||this.equals(Border.BOTTOM));
    }

}
