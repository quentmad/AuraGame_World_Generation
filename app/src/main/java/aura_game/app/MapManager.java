package aura_game.app;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;


public class MapManager {


    private TilesManager tilesManager;

    private SaveManager saveManager;
    private SpriteBatch batch;
    BitmapFont font;
    private ShapeRenderer shapeRenderer;

    private int dx = 0;
    private int dy = 0;


    private final int nbTilesWidth;
    private final int nbTilesHeight;
    private final int tileSize;

    private static final float NOISE_SCALE = 0.01f; // Échelle du Perlin noise

    private MapSaver mapSaver;

    private int[][] heightMap;//PERLIN1
    private double[][] temperatureMap;//PERLIN2
    private double[][] precipitationMap;//PERLIN3
    private double[][] treeDensityMap; // Bruit de Perlin pour les arbres
    /**
     * List des coordonnées des tuiles déjà vérifiées pour éviter de vérifier plusieurs fois la même tuile lors de la modification des tuiles problématiques
     */
    private List<Pair<Integer,Integer>> alreadyCheckedTiles = new ArrayList<>();
    /** Tableau 2D de Biomes, réalisé à partir des 3 cartes de bruit de Perlin */
    private Biome[][] biomeMap;
    private Tile[][] map;

    private int count = 0;

    public MapManager(int DEFAULT_NB_TILES_WIDTH, int DEFAULT_NB_TILES_HEIGHT, int TILE_SIZE) {

        this.batch = new SpriteBatch();
        this.tilesManager = new TilesManager(TILE_SIZE);
        this.saveManager = new SaveManager(tilesManager);
        this.map = new Tile[DEFAULT_NB_TILES_WIDTH][DEFAULT_NB_TILES_HEIGHT];
        heightMap = new int[DEFAULT_NB_TILES_WIDTH][DEFAULT_NB_TILES_HEIGHT];
        temperatureMap = new double[DEFAULT_NB_TILES_WIDTH][DEFAULT_NB_TILES_HEIGHT];
        precipitationMap = new double[DEFAULT_NB_TILES_WIDTH][DEFAULT_NB_TILES_HEIGHT];
        treeDensityMap = new double[DEFAULT_NB_TILES_WIDTH][DEFAULT_NB_TILES_HEIGHT];
        biomeMap = new Biome[DEFAULT_NB_TILES_WIDTH][DEFAULT_NB_TILES_HEIGHT];

        this.nbTilesWidth = DEFAULT_NB_TILES_WIDTH;
        this.nbTilesHeight = DEFAULT_NB_TILES_HEIGHT;
        this.tileSize = TILE_SIZE;
        this.mapSaver  = new MapSaver(tilesManager,nbTilesWidth,nbTilesHeight,tileSize );
        font = new BitmapFont();
        font.getData().setScale(0.7f);
        font.setColor(1, 0, 1, 1);
        shapeRenderer = new ShapeRenderer();

        int seed = (int)System.currentTimeMillis()%1000000;
        generateWorld(seed);
        System.out.println("Map generated with seed: " + seed);
       //generateWorld(1234567);
    }

    // Génère les différentes cartes de Perlin Noise et la carte des biomes, ainsi que les arbres
        public void generateWorld(long seed) {
            // Créer des PerlinNoise différents à partir de la même seed en ajoutant des offsets
            PerlinNoiseV3 heightNoise = new PerlinNoiseV3(seed);             // Utilise la seed originale
            PerlinNoiseV3 temperatureNoise = new PerlinNoiseV3(seed + 1200); // Seed décalée pour la température
            PerlinNoiseV3 precipitationNoise = new PerlinNoiseV3(seed + 2600); // Seed décalée pour les précipitations

            // Parcours de toute la carte pour remplir les maps
            for (int x = 0; x < nbTilesWidth; x++) {
                for (int y = 0; y < nbTilesHeight; y++) {
                    // Générer la hauteur avec une échelle pour avoir des montagnes/plaines
                    float height = heightNoise.noise(x, y, NOISE_SCALE);
                    //heightMap[x][y] = Math.round(((height + 1) / 2) * 10); // Normaliser entre 0 et 10
                    heightMap[x][y] = applyExponentialTransformation(((height + 1) / 2) * 11, 6.2f,1.8f);

                    // Générer la température avec une autre échelle si nécessaire
                    float temperature = temperatureNoise.noise(x, y, NOISE_SCALE /1.2f);
                    temperatureMap[x][y] = (temperature + 1) / 2; // Normaliser entre 0 et 1

                    // Générer la précipitation
                    float precipitation = precipitationNoise.noise(x, y, NOISE_SCALE/1.2f);
                    precipitationMap[x][y] = (precipitation + 1) / 2; // Normaliser entre 0 et 1

                    // Déterminer le biome en fonction des trois Perlin noise
                    biomeMap[x][y] = determineBiome(heightMap[x][y], temperatureMap[x][y], precipitationMap[x][y]);
                    if(biomeMap[x][y] == null) System.out.println("x: " + x + " y: " + y + " biome null "+ "   t: " + temperatureMap[x][y] );
                    //System.out.println("x: " + x + " y: " + y + " biome: " + biomeMap[x][y]+"   t: " + temperatureMap[x][y] );
                    //Déterminer la tuile à afficher en fonction du biome et de la hauteur
                    map[x][y] = tilesManager.getTile(biomeMap[x][y], heightMap[x][y]);

                }
            }
                //treeDensityMap[x][y] = treeDensity;
                //Virer les tuiles problématiques
                suppressAllProblematicTiles();//Agir sur heightMap
                addAllBorders();

                // Déterminer si un arbre est généré en fonction de la densité d'arbres et du biome
                //hasTreeMap[x][y] = determineTreePresence(biomeMap[x][y], treeDensity);

        }


    /**Transformer les hauteurs exponentiellement à partir d'un seuil*/
    public int applyExponentialTransformation(float value,float heightThreshold, float exponent) {
        // Si la hauteur est au-dessus du seuil, appliquer la transformation exponentielle
        if (value > heightThreshold) {
            float adjustedValue = (float) Math.pow((value - heightThreshold), exponent);
            return Math.round(heightThreshold + adjustedValue /1.8f);
        }
        return Math.round(value);
    }



            // Fonction pour déterminer le biome en fonction de la combinaison hauteur/température/précipitation
    private Biome determineBiome(double height, double temperature, double precipitation) {
        if(temperature < 0.24) {
            return Biome.SNOWY; // Neige
        }else if (height < 3.6) {
            return temperature < 0.7 ? Biome.OCEAN : Biome.HOTOCEAN; // Océan normal ou clearwater
        } else if (height < 6) {
            if (temperature < 0.4) {
                return Biome.TOUNDRA; // Toundra
            } else if (temperature > 0.6) {
                return precipitation > 0.6 ? Biome.JUNGLE: Biome.DESERT; // Jungle ou désert
            }
            return Biome.PLAINS; // Plaine
        } else {
            return (precipitation > 0.5) ? Biome.GRASSMOUNTAIN : Biome.MOUNTAIN; // Montagne
        }
    }

    /**
     * Supprime les tuiles problématiques et de les remplacer par les bonnes tuiles
     * Une tuile est supprimé si elle ne possède pas aux moins une tuile voisine verticalement et une horizontalement,
     * ou alors si il y a deux tuiles de la meme diagonale vide et l'autre diagonale non vide
     * Si une tuile est remplacé, on relance la méthode sur ces voisins de meme type/niveau pour être sur que leur cas n'est pas modifié*/
    public void suppressAllProblematicTiles(){
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                    determineAndReplaceTile(i,j);
            }
        }


    }

    /**
     * Détermine si la tuile à la position x,y est problématique, et la remplace si c'est le cas
     * Une tuile est problématique si elle ne possède pas de voisin horizontal ou vertical de même niveau, ou si elle possède un problème de diagonale
     * Si la tuile est remplacée, la méthode est relancée sur les voisins de même niveau pour voir si leur cas a changé
     */
    public void determineAndReplaceTile(int x, int y) {
        if(biomeMap[x][y].isWater()) return;
        //if(!isInBounds(x,y+1) || !isInBounds(x-1,y-1)) return;
        int currentTileLayer = heightMap[x][y];

        boolean isProblematic = !hasHorizontalNeighbor(x, y, currentTileLayer) || !hasVerticalNeighbor(x, y, currentTileLayer) || hasDiagonalIssue(x, y, currentTileLayer);
        if (isProblematic) {
            // Remplace la tuile par celle présente à la couche en dessous, et relance la méthode sur les voisins compatibles pour voir si leur cas a changé
            heightMap[x][y] -= 1;
            alreadyCheckedTiles.clear();

            map[x][y] = tilesManager.getTile(biomeMap[x][y], heightMap[x][y]);

            //Relance la méthode sur les voisins direct qui sont à la meme hauteur
            for (int i = -1; i <= 1; i++) {{
                    if (i != 0 && isInBounds(x+i, y) && currentTileLayer == heightMap[x + i][y] && !containsPair(alreadyCheckedTiles,x+i,y)) {
                        determineAndReplaceTile(x + i, y);
                    }else{
                        //Add in the list of already checked tiles
                        alreadyCheckedTiles.add(Pair.of(x+i,y));

                    }
                    if (i !=0 && isInBounds(x, y+i) && currentTileLayer == heightMap[x][y + i] && !containsPair(alreadyCheckedTiles,x,y+i)) {
                        determineAndReplaceTile(x, y + i);
                    }else{
                        //Add in the list of already checked tiles
                        alreadyCheckedTiles.add(Pair.of(x,y+i));
                    }
                }
            }

        }
    }

    private boolean containsPair(List<Pair<Integer,Integer>> list, int x, int y){
        for(Pair<Integer,Integer> p : list){
            if(p.getLeft() == x && p.getRight() == y) return true;
        }
        return false;
    }

    /**
     * Vérifie si la tuile à la position x,y possède un voisin horizontal de même niveau, ou si elle est en bord de map
     */
    private boolean hasHorizontalNeighbor(int x, int y, int currentTileLayer) {
        return (!isInBounds(x-1,y) || currentTileLayer == heightMap[x - 1][y]) ||(!isInBounds(x+1,y) || (currentTileLayer == heightMap[x + 1][y]));
    }

    private boolean hasVerticalNeighbor(int x, int y, int currentTileLayer) {
        return (!isInBounds(x,y+1) || (currentTileLayer == heightMap[x][y + 1])) || (!isInBounds(x, y-1) || (currentTileLayer == heightMap[x][y - 1]));
    }

    /**
     * Vérifie si la tuile à la position x,y possède un problème de diagonale
     * Un problème de diagonale est détecté si l'une des deux diagonales est remplie et l'autre vide
     */
    private boolean hasDiagonalIssue(int x, int y, int currentTileLayer) {
        if(!isInBounds(x+1,y+1) || !isInBounds(x-1,y-1) || !isInBounds(x+1,y-1) || !isInBounds(x-1,y+1)) return false;
        boolean diagonal1Filled =  currentTileLayer == heightMap[x+1][y+1] && currentTileLayer == heightMap[x-1][y-1];
        boolean diagonal2Filled =  currentTileLayer == heightMap[x+1][y-1] && currentTileLayer == heightMap[x-1][y+1];
        boolean diagonal1Empty =  currentTileLayer != heightMap[x+1][y+1] && currentTileLayer != heightMap[x-1][y-1];
        boolean diagonal2Empty =  currentTileLayer != heightMap[x+1][y-1] && currentTileLayer != heightMap[x-1][y+1];

        return (diagonal1Filled && diagonal2Empty) || (diagonal2Filled && diagonal1Empty);
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < nbTilesWidth && y >= 0 && y < nbTilesHeight ;
    }

    //  --------------------------------------BORDER----------------------------------------------
    private void addAllBorders() {
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                addBorders(i, j);
            }
        }
    }

    /**
     * Ajoute les bordures à la tuile à la position x,y
     * Pour cela verifie les voisins directs et diagonaux dont la hauteur est inférieure à la hauteur de la tuile actuelle
     * @param x
     * @param y
     */
    private void addBorders(int x, int y) {
        //Liste des voisins directs et diagonaux dont leur hauteur est inférieure à la hauteur de la tuile actuelle
        List<Orientation> directNeighborsLower = new ArrayList<>();
        List<Orientation> diagonalNeighborsLower = new ArrayList<>();//0 ou 1 tuile donc 0 ou 2 elements
        Tile underTile = null;

        //Horizontal neighbors
        if (isInBounds(x - 1, y) && isLayeredCorrectly(x,y,-1,0) && !biomeMap[x-1][y].isWater()) {
            directNeighborsLower.add(Orientation.LEFT);
        }
        if (isInBounds(x + 1, y) && isLayeredCorrectly(x,y,1,0) && !biomeMap[x+1][y].isWater()) {
            directNeighborsLower.add(Orientation.RIGHT);
        }
        //Vertical neighbors
        if (isInBounds(x, y - 1) && isLayeredCorrectly(x,y,0,-1) && !biomeMap[x][y-1].isWater()) {
            directNeighborsLower.add(Orientation.BOTTOM);
        }
        if (isInBounds(x, y + 1) && isLayeredCorrectly(x,y,0,1) && !biomeMap[x][y+1].isWater()) {
            directNeighborsLower.add(Orientation.TOP);
        }
        if(directNeighborsLower.size()==0) {
            //Diagonal neighbors
            if (isInBounds(x - 1, y - 1) && isLayeredCorrectly(x,y,-1,-1) && !biomeMap[x-1][y-1].isWater()) {
                diagonalNeighborsLower.add(Orientation.LEFT);
                diagonalNeighborsLower.add(Orientation.BOTTOM);
            }
            else if (isInBounds(x + 1, y - 1) && isLayeredCorrectly(x,y,1,-1) && !biomeMap[x+1][y-1].isWater()) {
                diagonalNeighborsLower.add(Orientation.RIGHT);
                diagonalNeighborsLower.add(Orientation.BOTTOM);
            }
            else if (isInBounds(x - 1, y + 1) && isLayeredCorrectly(x,y,-1,1) && !biomeMap[x-1][y+1].isWater()) {
                diagonalNeighborsLower.add(Orientation.LEFT);
                diagonalNeighborsLower.add(Orientation.TOP);
            }
            else if (isInBounds(x + 1, y + 1) && isLayeredCorrectly(x,y,1,1) && !biomeMap[x+1][y+1].isWater()) {
                diagonalNeighborsLower.add(Orientation.RIGHT);
                diagonalNeighborsLower.add(Orientation.TOP);
            }
        }

        Border borderType = Border.NOBORDER;

        //Priorité aux voisins directs
        switch (directNeighborsLower.size()) {
            case 1:
                borderType = Border.valueOf(""+directNeighborsLower.get(0));
                underTile = map[x+directNeighborsLower.get(0).dx()][y+directNeighborsLower.get(0).dy()];
                break;
            case 2:
                borderType = Border.valueOf("OUT"+directNeighborsLower.get(0)+directNeighborsLower.get(1));
                underTile = map[x+directNeighborsLower.get(0).dx()+directNeighborsLower.get(1).dx()][y+directNeighborsLower.get(0).dy()+directNeighborsLower.get(1).dy()];
                break;
            case 0:
                switch (diagonalNeighborsLower.size()) {
                    case 2:
                        borderType = Border.valueOf("IN"+diagonalNeighborsLower.get(0)+diagonalNeighborsLower.get(1));
                        underTile = map[x+diagonalNeighborsLower.get(0).dx()+diagonalNeighborsLower.get(1).dx()][y+diagonalNeighborsLower.get(0).dy()+diagonalNeighborsLower.get(1).dy()];
                        break;
                }
                break;

        }
        if(borderType.equals(Border.NOBORDER)) return;
        map[x][y]= new Tile(map[x][y].getTileType(), borderType, map[x][y].getLayer(), underTile);



    }


    /**
     * Vérifie si la tuile à la position x,y est correctement superposée à ses voisins
     * Une tuile est correctement superposée si elle est plus basse que ses voisins non aquatiques, et plus haute que ses voisins aquatiques
     */
    public boolean isLayeredCorrectly(int x, int y, int difx, int dify){
        return ((heightMap[x+difx][y+dify] < heightMap[x][y] && !biomeMap[x][y].isWater()) || (heightMap[x + difx][y + dify] >= heightMap[x][y] && biomeMap[x][y].isWater()) );
    }

    //  ------------------------------------------------------------------------------------------------

    /**Dessine l'ensemble des Tuiles déjà chargé dans le tableau2D au préalable*/
    public void drawMap(){
        batch.begin();
        count++;
        //Taille font de 2:
        dx-=3;
        dy-=3;
        font.getData().setScale(1.2f);
        for (int i = 0; i < nbTilesWidth; ++i) {
            for (int j = 0; j < nbTilesHeight; ++j) {
                Tile currentTile = map[i][j];

                tilesManager.drawTile(batch, currentTile, i * tileSize + dx, j * tileSize + dy);
                //font.draw(batch, ""+currentTile.getLayer(), i * tileSize + 15 + dx, j * tileSize + 15 + dy);
            }
        }
        batch.end();
    }

    public void renderBiomeMap() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int x = 0; x < nbTilesWidth; x++) {
            for (int y = 0; y < nbTilesHeight; y++) {

                shapeRenderer.setColor(biomeMap[x][y].colorMiniMap()); // Couleur du biome

                float tileSizeMiniMap = 1;
                shapeRenderer.rect(x*tileSizeMiniMap, y*tileSizeMiniMap, tileSizeMiniMap, tileSizeMiniMap); // Dessine la tuile
            }
        }

        shapeRenderer.end();


    }


    enum Orientation{
        TOP(0,1), RIGHT(1,0), BOTTOM(0,-1), LEFT(-1,0);
        private final int dx;
        private final int dy;

        Orientation(int x, int y){
            this.dx = x;
            this.dy = y;
        }

        public int dx(){
            return dx;
        }
        public int dy(){
            return dy;
        }

    }

}
