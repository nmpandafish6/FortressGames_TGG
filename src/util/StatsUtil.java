package util;

/**
 *
 * @author Nicolas
 */
public class StatsUtil {
    
    public static class Stats{
        private Stats(){}
        public double mean;
        public double max;
        public double min;
    }
    
    public static Stats getStats(double[][] data){
        Stats stats = new Stats();
        double max = -Double.MAX_VALUE;
        double min = Double.MAX_VALUE;
        double sum = 0;
        int vals = 0;
        for(int row = 0; row < data.length; row++){
            for(int col = 0; col < data[row].length; col++){
                if(data[row][col] > max){
                    max = data[row][col];
                }
                if(data[row][col] < min){
                    min = data[row][col];
                }
                sum += data[row][col];
                vals++;
            }
        }
        stats.max = max;
        stats.min = min;
        stats.mean = sum / vals;
        return stats;
    }
    
}
