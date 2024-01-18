package aura_game.app;

/**
 * Enumération représentant différents types de biomes avec des paramètres spécifiques pour personnaliser Perlin Noise.
 */
public enum Biome {
    ISLAND("ISLAND",3, 0.4, 2.0, 0.05, 0.55),
    LAKES("LAKES",1, 0.4, 2.0, 0.05, 0.94),
    MOUNTAINS("MOUNTAINS",5, 0.6, 2.0, 0.1, 1.4),
    PLAINS("PLAINS",3, 0.5, 2.0, 0.07, 1.1),
    DESERT("DESERT",5, 0.4, 1.8, 0.08, 1.2),
    FOREST("FOREST",5, 0.5, 2.0, 0.08, 0.95),
    SNOWY("SNOWY",5, 0.6, 2.3, 0.1, 1.5),
    HIGHLANDS("HIGHLANDS",3, 0.6, 2.1, 0.08, 1.6),
    TAIGA("TAIGA",5, 0.55, 2.1, 0.07, 1.3),
    TUNDRA("TUNDRA",5, 0.6, 2.2, 0.08, 1.4),
    JUNGLE("JUNGLE",5, 0.6, 2.1, 0.1, 1.3),
    BADLANDS("BADLANDS",5, 0.4, 1.8, 0.1, 1.8),

    ;

    private final String name;
    /**Nombre d'octaves (superpositions de bruit) - (Change le niveau de détail)
     * Default : 4 */
    private final int octaves;

    /**Persistance du bruit - (Change la rugosité)
     * Default : 0.5 */
    private final double persistence;

    /**Lacunarity du bruit - (Change la fréquence des détails)
     * Default : 2.0 */
    private final double lacunarity;

    /**Échelle globale du bruit - (Change la taille de la carte)
     * Default : 0.1 */
    private final double scale;
    /**Relief de la map
     * Default 1.0 */
    private final double relief;

    Biome(String name, int octaves, double persistence, double lacunarity, double scale, double relief) {
        this.name = name;
        this.octaves = octaves;
        this.persistence = persistence;
        this.lacunarity = lacunarity;
        this.scale = scale;
        this.relief = relief;
    }

    public String getName(){
        return name;
    }
    public int getOctaves() {
        return octaves;
    }

    public double getPersistence() {
        return persistence;
    }

    public double getLacunarity() {
        return lacunarity;
    }

    public double getScale() {
        return scale;
    }

    public double getRelief() {
        return relief;
    }
}
