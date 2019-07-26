package view;

public interface Commons {
     int BOARD_WIDTH = 750;
     int BOARD_HEIGHT = 750;

     //PLAYER
     int PLAYER_WIDTH = 104;
     int PLAYER_HEIGHT = 100;
     int PLAYER_SPEED = 4;

     int START_X = (BOARD_WIDTH-PLAYER_WIDTH)/2;
     int START_Y = (BOARD_HEIGHT-190);
     int START_HEALTH = 100;

     int SHOT_WIDTH = 20;
     int SHOT_HEIGHT = 28;

     //MINION

     int MINION_INIT_X = 5;
     int MINION_INIT_Y = 15;

     int MINION_WIDTH = 42;
     int MINION_HEIGHT = 43;
     int MINION_SPEED = 300;

     int MINION_GO_DOWN = 15;
     int MINION_DIRECTION = 8;

     int MINION_ATTACK_WIDTH = 15;
     int MINION_ATTACK_HEIGHT = 25;
     int MINION_ATTACK_SPEED = 8;

     //HEALTH BAR
     int HEALTHBAR_HEIGHT = 20;
     int HEALTHBAR_INIT_X = 100;
     int HEALTHBAR_INIT_Y = BOARD_HEIGHT-85;


    //SpriteSheet

    //Background
    SpriteSheet bgSS = SpriteSheet.CreateSpriteArray("background.png", 1, 1, BOARD_WIDTH, BOARD_HEIGHT, 1);

    //Minion
        //Minion
        SpriteSheet minionSS = SpriteSheet.CreateSpriteArray("minionSprites.png", 1,8,MINION_WIDTH,MINION_HEIGHT,8);
        //Minion Attack
        SpriteSheet minionAtackSS = SpriteSheet.CreateSpriteArray("minionAttack.png", 1, 1, MINION_ATTACK_WIDTH, MINION_ATTACK_HEIGHT, 1);


    //Player
        //Player
        SpriteSheet playerSS = SpriteSheet.CreateSpriteArray("player.png", 1,3,PLAYER_WIDTH,PLAYER_HEIGHT,3);
        //Music Note
        SpriteSheet musicNoteSS = SpriteSheet.CreateSpriteArray("musicNote.png", 1,2,PLAYER_WIDTH,PLAYER_HEIGHT,2);
        //Base
        SpriteSheet baseSS = SpriteSheet.CreateSpriteArray("base.png", 1,4,PLAYER_WIDTH,PLAYER_HEIGHT,4);
        //Player Attack
        SpriteSheet shotSS = SpriteSheet.CreateSpriteArray("shots.png", 1,6,SHOT_WIDTH,SHOT_HEIGHT,6);
}
