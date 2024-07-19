package cmpt276.project.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import cmpt276.project.GUI.Options;

public class GameLogic {
    private int[][] deckLocations; // index tells you where, number tells you which
    private int[][] discardLocations;
    private int imgSet;
    private int order;
    private int cardsLeft;
    private int length;
    private int maxCards;
    private boolean[] usedCards;
    private int[] randImages;
    private int mode;
    private int difficulty;
    private Map<Integer, Boolean> deckIsImg = new HashMap<>();
    private Map<Integer, Boolean> discardIsImg = new HashMap<>();
    private Map<Integer, Integer> deckRotation = new HashMap<>();
    private Map<Integer, Integer> discardRotation = new HashMap<>();
    private Map<Integer, Double> deckSize = new HashMap<>();
    private Map<Integer, Double> discardSize = new HashMap<>();
    private ArrayList<String> animeFiles = new ArrayList<>();
    private ArrayList<String> gameCharFiles = new ArrayList<>();
    private ArrayList<String> whiteManFiles = new ArrayList<>();


    public ArrayList<Bitmap> getOwn() {
        return own;
    }

    private ArrayList<Bitmap> own = new ArrayList<>();
//    private ArrayList<String> animeFiles = new ArrayList<>();
//    private ArrayList<String> gameCharFiles = new ArrayList<>();
//    private ArrayList<String> whiteManFiles = new ArrayList<>();
    private ArrayList<ImageSet> imageSets = new ArrayList<>();
    private int[][] currentOrderCards;
    private final int[][] orderTwoCards = {{0, 1, 4}, {2, 3, 4}, {0, 2, 5}, {1, 3, 5}, {0, 3, 6}, {1, 2, 6}, {4, 5, 6}};
    private final int[][] orderThreeCards = {{0, 1, 2, 9}, {9, 3, 4, 5}, {8, 9, 6, 7}, {0, 10, 3, 6}, {1, 10, 4, 7}, {8, 2, 10, 5}, {0, 8, 11, 4}, {1, 11, 5, 6}, {11, 2, 3, 7}, {0, 12, 5, 7}, {8, 1, 3, 12}, {12, 2, 4, 6}, {9, 10, 11, 12}};
    private final int[][] orderFiveCards = {{0, 1, 2, 3, 4, 25}, {5, 6, 7, 8, 9, 25}, {10, 11, 12, 13, 14, 25}, {15, 16, 17, 18, 19, 25}, {20, 21, 22, 23, 24, 25}, {0, 5, 10, 15, 20, 26}, {1, 6, 11, 16, 21, 26}, {2, 7, 12, 17, 22, 26},
            {3, 8, 13, 18, 23, 26}, {4, 9, 14, 19, 24, 26}, {0, 6, 12, 18, 24, 27}, {1, 7, 13, 19, 20, 27}, {2, 8, 14, 15, 21, 27}, {3, 9, 10, 16, 22, 27}, {4, 5, 11, 17, 23, 27}, {0, 7, 14, 16, 23, 28}, {1, 8, 10, 17, 24, 28}, {2, 9, 11, 18, 20, 28},
            {3, 5, 12, 19, 21, 28}, {4, 6, 13, 15, 22, 28}, {0, 8, 11, 19, 22, 29}, {1, 9, 12, 15, 23, 29}, {2, 5, 13, 16, 24, 29}, {3, 6, 14, 17, 20, 29}, {4, 7, 10, 18, 21, 29}, {0, 9, 13, 17, 21, 30}, {1, 5, 14, 18, 22, 30}, {2, 6, 10, 19, 23, 30},
            {3, 7, 11, 15, 24, 30}, {4, 8, 12, 16, 20, 30}, {25, 26, 27, 28, 29, 30}};
    private final String tag = "gameLogic"; // Log tag for debugging

    public void setAdd(String save) {
        this.save.add(save);
    }

    public Set<String> getSave() {
        return save;
    }

    private Set<String> save =new HashSet<String>();


    public Bitmap getOwnAt(int i) {
        return this.own.get(i);
    }
    public void addOwn(Bitmap own) {
        this.own.add(own);
    }
    // Singleton code
    private static GameLogic instance;

    private GameLogic() {
        ImageSet animeFiles = new ImageSet();
        animeFiles.addFileName("aot");
        animeFiles.addFileName("ash");
        animeFiles.addFileName("bleach");
        animeFiles.addFileName("dbz");
        animeFiles.addFileName("fairytail");
        animeFiles.addFileName("naruto");
        animeFiles.addFileName("onepunchman");
        animeFiles.addFileName("sds");
        animeFiles.addFileName("aqua");
        animeFiles.addFileName("asuna");
        animeFiles.addFileName("boruto");
        animeFiles.addFileName("conan");
        animeFiles.addFileName("edward");
        animeFiles.addFileName("esdeath");
        animeFiles.addFileName("gatamon");
        animeFiles.addFileName("gintoki");
        animeFiles.addFileName("hinata");
        animeFiles.addFileName("jotaro");
        animeFiles.addFileName("killua");
        animeFiles.addFileName("koro");
        animeFiles.addFileName("kousei");
        animeFiles.addFileName("midoriya");
        animeFiles.addFileName("onodera");
        animeFiles.addFileName("rimuru");
        animeFiles.addFileName("ryuk");
        animeFiles.addFileName("setokaiba");
        animeFiles.addFileName("soma");
        animeFiles.addFileName("steinsgate");
        animeFiles.addFileName("violet");
        animeFiles.addFileName("yang");
        animeFiles.addFileName("yato");

        ImageSet gameCharFiles = new ImageSet();
        gameCharFiles.addFileName("chief");
        gameCharFiles.addFileName("cloud");
        gameCharFiles.addFileName("yasuo");
        gameCharFiles.addFileName("chrom");
        gameCharFiles.addFileName("geralt");
        gameCharFiles.addFileName("link");
        gameCharFiles.addFileName("mario");
        gameCharFiles.addFileName("samus");
        gameCharFiles.addFileName("banjo");
        gameCharFiles.addFileName("crash");
        gameCharFiles.addFileName("dante");
        gameCharFiles.addFileName("donkeykong");
        gameCharFiles.addFileName("ezio");
        gameCharFiles.addFileName("faux");
        gameCharFiles.addFileName("fortnite");
        gameCharFiles.addFileName("laracroft");
        gameCharFiles.addFileName("lightning");
        gameCharFiles.addFileName("mccree");
        gameCharFiles.addFileName("pacman");
        gameCharFiles.addFileName("peach");
        gameCharFiles.addFileName("pit");
        gameCharFiles.addFileName("rayman");
        gameCharFiles.addFileName("ryu");
        gameCharFiles.addFileName("sans");
        gameCharFiles.addFileName("sonic");
        gameCharFiles.addFileName("spyro");
        gameCharFiles.addFileName("tomnook");
        gameCharFiles.addFileName("tracer");
        gameCharFiles.addFileName("yoshi");
        gameCharFiles.addFileName("jin");
        gameCharFiles.addFileName("terry");

        ImageSet whiteManFiles = new ImageSet();
        whiteManFiles.addFileName("electricdrill");
        whiteManFiles.addFileName("prepareluggage");
        whiteManFiles.addFileName("puzzle");
        whiteManFiles.addFileName("trophy");
        whiteManFiles.addFileName("battery");
        whiteManFiles.addFileName("blackboard");
        whiteManFiles.addFileName("books");
        whiteManFiles.addFileName("bulb");
        whiteManFiles.addFileName("clickbutton");
        whiteManFiles.addFileName("dice");
        whiteManFiles.addFileName("draw");
        whiteManFiles.addFileName("email");
        whiteManFiles.addFileName("handshake");
        whiteManFiles.addFileName("lock");
        whiteManFiles.addFileName("luggage");
        whiteManFiles.addFileName("omg");
        whiteManFiles.addFileName("painting");
        whiteManFiles.addFileName("readbook");
        whiteManFiles.addFileName("reclycle");
        whiteManFiles.addFileName("resistance");
        whiteManFiles.addFileName("screw");
        whiteManFiles.addFileName("setting");
        whiteManFiles.addFileName("takebook");
        whiteManFiles.addFileName("takenotes");
        whiteManFiles.addFileName("talking");
        whiteManFiles.addFileName("touchhead");
        whiteManFiles.addFileName("touchdraw");
        whiteManFiles.addFileName("twobuttons");
        whiteManFiles.addFileName("whiteboard");
        whiteManFiles.addFileName("wifi");
        whiteManFiles.addFileName("wrench");

        this.imageSets.add(animeFiles);
        this.imageSets.add(gameCharFiles);
        this.imageSets.add(whiteManFiles);
    }

    public static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    // set the order which user selected
    public void setOrder (int order) {
        this.order = order;
        this.maxCards = order * order + order + 1;
        this.usedCards = new boolean[maxCards];
        if (order == 2) {
            this.currentOrderCards = this.orderTwoCards;
        } else if (order == 3) {
            this.currentOrderCards = this.orderThreeCards;
        } else if (order == 5) {
            this.currentOrderCards = this.orderFiveCards;
        }
    }

    // -1 indicates all. Note that this MUST be called after the order is set
    public void setCardsLeft (int cards) {
        if (cards == -1) {
            this.cardsLeft = this.maxCards;
        } else {
            this.cardsLeft = cards;
        }
    }

    public int getLength() {
        if (imgSet == 0) {
            return animeFiles.size();
        } else if (imgSet == 1) {
            return gameCharFiles.size();
        } else if (imgSet == 2) {
            return whiteManFiles.size();}
            else
                return -3;
    }

    // zero for anime, 1 for game chars, 2 for white man, ++ for each new set
    public void setImgSet(int imgSet) {
        this.imgSet = imgSet;
    }

    // zero for images, 1 for image & words
    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getImgName(int index) {
        return imageSets.get(this.imgSet).getFileName(index);
    }

    public boolean getDeckIsImg(int key) {
        if (this.deckIsImg.get(key) == null) {
            return false;
        } else {
            return this.deckIsImg.get(key);
        }
    }

    public boolean getDiscardIsImg(int key) {
        if (this.discardIsImg.get(key) == null) {
            return false;
        } else {
            return this.discardIsImg.get(key);
        }
    }

    public int getDeckRotation(int key) {
        if (deckRotation.get(key) == null) {
            return 0;
        } else {
            return deckRotation.get(key);
        }
    }

    public int getDiscardRotation(int key) {
        if (discardRotation.get(key) == null) {
            return 0;
        } else {
            return discardRotation.get(key);
        }
    }

    public double getDeckSize(int key) {
        if (deckSize.get(key) == null) {
            return 0;
        } else {
            return deckSize.get(key);
        }
    }

    public double getDiscardSize(int key) {
        if (discardSize.get(key) == null) {
            return 0;
        } else {
            return discardSize.get(key);
        }
    }

    // chooses initial locations for all images
    public void initLocations() {
        // choose two random cards
        Random rand = new Random();
        // 0 is deck, 1 is discard
        this.randImages = rand.ints(0, this.maxCards).distinct().limit(2).toArray();
        this.usedCards[randImages[0]] = true;
        this.usedCards[randImages[1]] = true;
        this.deckLocations = new int[3][3];
        this.discardLocations = new int[3][3];

        // while there's images to place, randomly choose positions until one is empty
        int imgNumber;
        for (int i = 0; i <= order; ++i) {
            int imgX;
            int imgY;
            do {
                imgX = rand.nextInt(3);
                imgY = rand.nextInt(3);
            } while (deckLocations[imgX][imgY] != 0);
            imgNumber = currentOrderCards[randImages[0]][i] + 1;
            this.deckLocations[imgX][imgY] = imgNumber;
            // game mode code
            if (this.mode == 0) {
                this.deckIsImg.put(imgNumber, true);
            } else if (this.mode == 1) {
                // if the mode is images & words
                if (this.deckIsImg.isEmpty()) {
                    // if it's empty we need an image
                    deckIsImg.put(imgNumber, true);
                } else if (deckIsImg.size() == 1) {
                    // if there's one key already we need text
                    deckIsImg.put(imgNumber, false);
                } else if (rand.nextFloat() < 0.5) {
                    // otherwise we're free to put one randomly
                    deckIsImg.put(imgNumber, false);
                } else {
                    deckIsImg.put(imgNumber, true);
                }
            }
            // difficulty rotation/size code
            if (this.difficulty == 0) {
                // if easy no rotation, no size change
                deckRotation.put(imgNumber, 0);
                deckSize.put(imgNumber, 0.8);
            } else if (this.difficulty == 1) {
                // if normal rotation in range [15, 360)
                deckRotation.put(imgNumber, randRotation());
                deckSize.put(imgNumber, 0.8);
            } else if (difficulty == 2) {
                // if hard rotation plus size [0.5, 1.0)
                deckRotation.put(imgNumber, randRotation());
                deckSize.put(imgNumber, randSize());
            }

            do {
                imgX = rand.nextInt(3);
                imgY = rand.nextInt(3);
            } while (discardLocations[imgX][imgY] != 0);
            imgNumber = currentOrderCards[randImages[1]][i] + 1;
            this.discardLocations[imgX][imgY] = imgNumber;
            if (this.mode == 0) {
                discardIsImg.put(imgNumber, true);
            } else if (this.mode == 1) {
                // if the mode is images & words
                if (discardIsImg.isEmpty()) {
                    Log.e(tag, "first img placed");
                    // if it's empty we need an image
                    discardIsImg.put(imgNumber, true);
                } else if (discardIsImg.size() == 1) {
                    Log.e(tag, "first text placed");
                    // if there's one key already we need text
                    discardIsImg.put(imgNumber, false);
                } else if (rand.nextFloat() < 0.5) {
                    Log.e(tag, "Check random fail");
                    // otherwise we're free to put one randomly
                    discardIsImg.put(imgNumber, false);
                } else {
                    Log.e(tag, "check random success");
                    discardIsImg.put(imgNumber, true);
                }
            }

            if (this.difficulty == 0) {
                discardRotation.put(imgNumber, 0);
                discardSize.put(imgNumber, 0.8);
            } else if (this.difficulty == 1) {
                discardRotation.put(imgNumber, randRotation());
                discardSize.put(imgNumber, 0.8);
            } else if (difficulty == 2) {
                discardRotation.put(imgNumber, randRotation());
                discardSize.put(imgNumber, randSize());
            }
        }

        this.cardsLeft -= 2;
    }

    // zero for not an image location, index of image otherwise
    public int deckIndex(int x, int y) {
        return (this.deckLocations[x][y]);
    }

    public int discardIndex(int x, int y) {
        return (this.discardLocations[x][y]);
    }

    public boolean isMatch(int index) {
        for (int i = 0; i <= order; ++i) {
            if (currentOrderCards[randImages[1]][i] + 1 == index) {
                return true;
            }
        }
        return false;
    }

    // move cards from deck to discard, randomly choosing a new card for the deck
    public void moveCards() {
        // bounds checking
        if (this.cardsLeft == 0) {
            return;
        }
        // Copy values. Shallow for arrays, deep for maps
        discardLocations = deckLocations;
        randImages[1] = randImages[0];
        discardIsImg.clear();
        discardIsImg.putAll(deckIsImg);
        discardRotation.clear();
        discardRotation.putAll(deckRotation);
        discardSize.clear();
        discardSize.putAll(deckSize);
        // randomly choose an unused card
        this.deckLocations = new int[3][3];
        Random rand = new Random();
        do {
            randImages[0] = rand.nextInt(this.maxCards);
        } while (usedCards[randImages[0]]);
        this.usedCards[randImages[0]] = true;
        // randomly place images from card
        deckIsImg.clear();
        deckRotation.clear();
        deckSize.clear();
        for (int i = 0; i <= order; ++i) {
            int imgX;
            int imgY;
            do {
                imgX = rand.nextInt(3);
                imgY = rand.nextInt(3);
            } while (deckLocations[imgX][imgY] != 0);
            int imgNumber = currentOrderCards[randImages[0]][i] + 1;
            this.deckLocations[imgX][imgY] = imgNumber;
            // setting random image or text
            if (this.mode == 0) {
                deckIsImg.put(imgNumber, true);
            } else if (this.mode == 1) {
                // if the mode is images & words
                if (deckIsImg.isEmpty()) {
                    // if it's empty we need an image
                    deckIsImg.put(imgNumber, true);
                } else if (deckIsImg.size() == 1) {
                    // if there's one key already we need text
                    deckIsImg.put(imgNumber, false);
                } else if (rand.nextFloat() < 0.5) {
                    // otherwise we're free to put one randomly
                    deckIsImg.put(imgNumber, false);
                } else {
                    deckIsImg.put(imgNumber, true);
                }
            }
            // setting random rotations
            if (this.difficulty == 0) {
                deckRotation.put(imgNumber, 0);
                deckSize.put(imgNumber, 0.8);
            } else if (this.difficulty == 1) {
                deckRotation.put(imgNumber, randRotation());
                deckSize.put(imgNumber, 0.8);
            } else if (difficulty == 2) {
                deckRotation.put(imgNumber, randRotation());
                deckSize.put(imgNumber, randSize());
            }
        }
        this.cardsLeft--;
    }

    public int getCardsLeft() {
        return cardsLeft;
    }

    public boolean isValidOrder(int imgSet, int order) {
        if (imgSet == 3){
            GameLogic gameLogic = GameLogic.getInstance();
            return gameLogic.getSave().size() >=order * order + order + 1;
        }
        Log.e(tag, "" + this.imageSets.get(imgSet).getSize());
        return this.imageSets.get(imgSet).getSize() >= order * order + order + 1;
    }

    public static int defaultScore(int position, int order, int deckSize) {
        final int[] baseScores = {5, 6, 7, 8, 9};
        final int[] orderModifier = {2, 3, 5};
        final int[] deckSizeModifier = {5, 10, 15, 20, 31};

        return baseScores[position] * orderModifier[order] + deckSizeModifier[deckSize];
    }

    private int randRotation() {
        Random rand = new Random();
        return rand.nextInt(320) + 20;
    }

    private double randSize() {
        Random rand = new Random();
        return 0.5 * rand.nextDouble() + 0.5;
    }
}