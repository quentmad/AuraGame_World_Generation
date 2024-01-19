package aura_game.app;

/**
 * Enumération représentant différents types de biomes avec des paramètres spécifiques pour personnaliser Perlin Noise.
 */
public enum Biome {
    ISLAND("ISLAND", 3, 0.4, 2.0, 0.05, 0.55) {
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER,BorderType.NOBORDER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.GRASS,BorderType.NOBORDER, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 4);
            } else {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 5);
            }
        }
    },
    LAKES("LAKES", 1, 0.4, 2.0, 0.05, 0.94) {
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER,BorderType.NOBORDER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.GRASS,BorderType.NOBORDER, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.WATER, BorderType.NOBORDER, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 4);
            } else {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 5);
            }
        }
    },

    MOUNTAINS("MOUNTAINS", 5, 0.6, 2.0, 0.1, 1.4) {
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER,BorderType.NOBORDER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.GRASS,BorderType.NOBORDER, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.MOUNTAIN1, BorderType.NOBORDER, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.MOUNTAIN2, BorderType.NOBORDER, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.MOUNTAIN3, BorderType.NOBORDER, 4);
            } else {
                return new Tile(TileType.MOUNTAIN4, BorderType.NOBORDER, 5);
            }
        }
    },
    PLAINS("PLAINS",3, 0.5, 2.0, 0.07, 1.1){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER,BorderType.NOBORDER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.GRASS,BorderType.NOBORDER, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 4);
            } else {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 5);
            }
        }
    },
    DESERT("DESERT",5, 0.4, 1.8, 0.08, 1.2){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER,BorderType.NOBORDER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.GRASS,BorderType.NOBORDER, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.MOUNTAIN1, BorderType.NOBORDER, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.MOUNTAIN1, BorderType.NOBORDER, 4);
            } else {
                return new Tile(TileType.MOUNTAIN2, BorderType.NOBORDER, 5);
            }
        }
    },
    FOREST("FOREST",5, 0.5, 2.0, 0.08, 0.95){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER,BorderType.NOBORDER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.GRASS,BorderType.NOBORDER, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 4);
            } else {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 5);
            }
        }
    },
    SNOWY("SNOWY",5, 0.6, 2.3, 0.1, 1.5){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER,BorderType.NOBORDER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.GRASS,BorderType.NOBORDER, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.MOUNTAIN3, BorderType.NOBORDER, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.MOUNTAIN3, BorderType.NOBORDER, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.MOUNTAIN3, BorderType.NOBORDER, 4);
            } else {
                return new Tile(TileType.MOUNTAIN3, BorderType.NOBORDER, 5);
            }
        }
    },
    HIGHLANDS("HIGHLANDS",3, 0.6, 2.1, 0.08, 1.6){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER,BorderType.NOBORDER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.GRASS,BorderType.NOBORDER, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.MOUNTAIN1, BorderType.NOBORDER, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.MOUNTAIN2, BorderType.NOBORDER, 4);
            } else {
                return new Tile(TileType.MOUNTAIN1, BorderType.NOBORDER, 5);
            }
        }
    },
    TAIGA("TAIGA",5, 0.55, 2.1, 0.07, 1.3){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER,BorderType.NOBORDER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.MOUNTAIN1,BorderType.NOBORDER, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.MOUNTAIN1, BorderType.NOBORDER, 4);
            } else {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 5);
            }
        }
    },
    TUNDRA("TUNDRA",5, 0.6, 2.2, 0.08, 1.4){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER,BorderType.NOBORDER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.MOUNTAIN1,BorderType.NOBORDER, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.MOUNTAIN1, BorderType.NOBORDER, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 4);
            } else {
                return new Tile(TileType.MOUNTAIN2, BorderType.NOBORDER, 5);
            }
        }
    },
    JUNGLE("JUNGLE",5, 0.6, 2.1, 0.1, 1.3){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER,BorderType.NOBORDER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.GRASS,BorderType.NOBORDER, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 4);
            } else {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 5);
            }
        }
    },
    BADLANDS("BADLANDS",5, 0.4, 1.8, 0.1, 1.8){
        @Override
        public Tile getTileFor(double n) {
            if (n < 0.38) {
                return new Tile(TileType.WATER,BorderType.NOBORDER, 0);
            } else if (n >= 0.38 && n < 0.7) {
                return new Tile(TileType.MOUNTAIN1,BorderType.NOBORDER, 1) ;
            } else if (n >= 0.7 && n < 0.85) {
                return new Tile(TileType.GRASS, BorderType.NOBORDER, 2);
            } else if (n >= 0.85 && n < 0.95) {
                return new Tile(TileType.MOUNTAIN1, BorderType.NOBORDER, 3);
            } else if (n >= 0.95 && n < 1.12) {
                return new Tile(TileType.MOUNTAIN1, BorderType.NOBORDER, 4);
            } else {
                return new Tile(TileType.MOUNTAIN2, BorderType.NOBORDER, 5);
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
