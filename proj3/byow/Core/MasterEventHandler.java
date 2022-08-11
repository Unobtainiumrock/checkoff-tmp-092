package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.event.*;
import java.util.Arrays;

public class MasterEventHandler implements MouseListener {

    public MasterEventHandler(Board b) {
//        Arrays.stream(b.getBoardTiles()).map((tile) -> {
//
//        });
        TETile test = Tileset.NOTHING;

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getSource());
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
