/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package terraingenerator;

/**
 *
 * @author Nicolas
 */
public class TerrainGenData{
        public TerrainGenData(Coordinate coord, int msize, boolean type){
            this.coord = coord;
            this.size = msize;
            this.type = type;
        }
        public Coordinate coord;
        public int size;
        public boolean type;// true for diamond
}
