package aura_game.app;

/**
 * Enumération représentant différents types de biomes avec des paramètres spécifiques pour personnaliser Perlin Noise.
 */
public enum Biome {
    ISLAND("ISLAND", 3, 0.4, 1.3, 0.05, 1.1) {
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.70) {
                return new Tile(TileType.CLEARWATER, 0);
            //} else if (n >= 0.38 && n < 0.7) {
            //    return new Tile(TileType.SAND, 1) ;
            } else if (n >= 0.7 && n < 0.80) {
                return new Tile(TileType.SAND, 1);
            } else if (n >= 0.80 && n < 0.95) {
                return new Tile(TileType.GRASS, 2);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.GRASS, 3);
            } else {
                return new Tile(TileType.GRASS, 4);
            }
        }
    },
    LAKES("LAKES", 1, 0.4, 1.5, 0.05, 1) {
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.CLEARWATER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.DARKGRASS, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.DARKGRASS, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.DARKSAND, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.DARKGRASS, 4);
            } else {
                return new Tile(TileType.DARKGRASS, 5);
            }
        }
    },
    SWAMP("SWAMP", 4, 0.5, 1.4, 0.1, 1.4) {
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.DARKWATER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.DARKWATER, 0) ;
            } else if (n >= 0.7 && n < 0.82) {
                return new Tile(TileType.DIRT, 2);
            } else if (n >= 0.82 && n < 0.95) {
                return new Tile(TileType.DARKGRASS, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.DARKGRASS, 4);
            } else {
                return new Tile(TileType.DIRT, 5);
            }
        }
    },

    MOUNTAINS("MOUNTAINS", 5, 0.6, 1.3, 0.08, 1.55){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.GRASS, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.DIRT, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.DIRT, 3);
            } else if (n >= 0.95 && n < 1.08) {
                return new Tile(TileType.GRASS, 4);
            } else {
                return new Tile(TileType.SNOW, 5);
            }
        }
    },
    PLAINS("PLAINS",2, 0.5, 2.0, 0.06, 1.1){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.GRASS, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.GRASS, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.GRASS, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.GRASS, 4);
            } else {
                return new Tile(TileType.GRASS, 5);
            }
        }
    },
    DESERT("DESERT",4, 0.5, 1.0, 0.06, 1.15){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.DARKSAND, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.DARKSAND, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.DARKSAND, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.DARKSAND, 4);
            } else {
                return new Tile(TileType.GRASS, 5);
            }
        }
    },
    FOREST("FOREST",2, 0.5, 1.0, 0.08, 1){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.3) {
                return new Tile(TileType.WATER, 0);
            } else if (n >= 0.3 && n < 0.7) {
                return new Tile(TileType.DARKGRASS, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.DARKGRASS, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.DIRT, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.DARKGRASS, 4);
            } else {
                return new Tile(TileType.DARKGRASS, 5);
            }
        }
    },
    SNOWY("SNOWY",3, 0.5, 1.3, 0.06, 1.45){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.SNOW, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.SNOW, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.SNOW, 3);
            } else if (n >= 0.95 && n < 1.08) {
                return new Tile(TileType.SNOW, 4);
            } else {
                return new Tile(TileType.SNOW, 5);
            }
        }
    },
    HIGHLANDS("HIGHLANDS",3, 0.5, 1.2, 0.06, 1.6){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.GRASS, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.GRASS, 2);
            } else if (n >= 0.85 && n < 0.96) {
                return new Tile(TileType.GRASS, 3);
            } else if (n >= 0.96 && n < 1.08) {
                return new Tile(TileType.DIRT, 4);
            } else if (n >= 1.08 && n < 1.20) {
                return new Tile(TileType.DIRT, 5);
            }else{
                return new Tile(TileType.GRASS, 6);
            }
        }
    },
    TAIGA("TAIGA",5, 0.55, 1.5, 0.07, 1.3){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.48) {
                return new Tile(TileType.WATER, 0);
            } else if (n >= 0.48 && n < 0.7) {
                return new Tile(TileType.DARKGRASS, 1) ;
            } else if (n >= 0.4 && n < 0.85) {
                return new Tile(TileType.DARKGRASS, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.DARKGRASS, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.DARKGRASS, 4);
            } else {
                return new Tile(TileType.DARKGRASS, 5);
            }
        }
    },
    TUNDRA("TUNDRA",5, 0.5, 1.3, 0.08, 1.4){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.CLEARGRASS, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.CLEARGRASS, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.CLEARGRASS, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.DIRT, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.SNOW, 4);
            } else {
                return new Tile(TileType.SNOW, 5);
            }
        }
    },
    JUNGLE("JUNGLE",5, 0.6, 1.3, 0.1, 1.4){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.DARKGRASS, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.DARKGRASS, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.DARKGRASS, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.DARKGRASS, 4);
            } else {
                return new Tile(TileType.DIRT, 5);
            }
        }
    },
    BADLANDS("BADLANDS",5, 0.4, 1.6, 0.1, 1.2){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER, 0);
            } else if (n >= 0.38 && n < 0.5) {
                return new Tile(TileType.DARKSAND, 1) ;
            } else if (n >= 0.5 && n < 0.63) {
                return new Tile(TileType.DIRT, 2);
            } else if (n >= 0.63 && n < 0.7) {
                return new Tile(TileType.DIRT, 3);
            } else if (n >= 0.7 && n < 1.83) {
                return new Tile(TileType.DIRT, 4);
            } else if (n >= 1.83 && n < 1.95) {
                return new Tile(TileType.DIRT, 5);
            } else if (n >= 1.95 && n < 2.00) {
                return new Tile(TileType.DIRT, 5);
            }else{
                return new Tile(TileType.DIRT, 6);
            }
        }
    }

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

    /**
     * Retourne la tile selon le biome pour n.
     * @param n détermine l'échelle du bruit dans chaque dimension
     * @return
     */
    public abstract Tile getTileFor(double n);
}
