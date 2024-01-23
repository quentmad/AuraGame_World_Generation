package aura_game.app;

/**Différents type de bordure possible.
 * La direction fait référence à la direction vers laquelle se trouve l'herbe... de la texture
 */
public enum Border {
    RIGHT_BOTTOM, BOTTOM, BOTTOM_LEFT,
    RIGHT, NOBORDER, LEFT,
    RIGHT_TOP, TOP, TOP_LEFT;

    /**@return true si c'est un bord simple (top, right, left, bottom)*/
    public boolean isSimpleBorder(){
        return (this.equals(Border.TOP) ||this.equals(Border.RIGHT)||this.equals(Border.LEFT)||this.equals(Border.BOTTOM));
    }

}
