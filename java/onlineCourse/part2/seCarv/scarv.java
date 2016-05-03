import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {

    private int [][] colorArray;
    private int picWidth;
    private int picHeight;

    /*create a seam carver object based on the given picture*/
    public SeamCarver(Picture picture)
    {
        if (null == picture)
            throw new java.lang.NullPointerException();

        picWidth = picture.width();
        picHeight = picture.height();
        colorArray = new int [picWidth][picHeight];
        for (int x = 0; x < picWidth; x++)
        {
            for (int y = 0; y < picHeight; y++)
            {
                colorArray[x][y] = picture.get(x, y).getRGB();
            }
        }
    }

    private boolean horSeamValidation(int [] tab)
    {
        boolean result = true;

        if (tab.length != picWidth)
            result = false;

        if (result)
        {
            /*Check if idx'es are neighbours*/
            for (int idx = 0; idx < tab.length - 1; idx++)
            {
                int diff = tab[idx] - tab[idx + 1];
                if ((Math.abs(diff) > 1) || (tab[idx] < 0) || (tab[idx] > (picHeight - 1)))
                {
                    result = false;
                    break;
                }
            }
            
            if ((tab[tab.length - 1] < 0) || (tab[tab.length -1] > (picHeight - 1)))
            {
                result = false;
            }
        }

        return result;
    }

    private boolean verSeamValidation(int [] tab)
    {
        boolean result = true;

        if (tab.length != picHeight)
            result = false;

        if (result)
        {
            for (int idx = 0; idx < tab.length - 1; idx++)
            {
                int diff = tab[idx] - tab[idx + 1];
                if ((Math.abs(diff) > 1) || (tab[idx] < 0) || (tab[idx] > (picWidth - 1)))
                {
                    result = false;
                    break;
                }
            }
            if ((tab[tab.length - 1] < 0) || (tab[tab.length -1] > (picWidth - 1)))
            {
                result = false;
            }
        }

        return result;
    }

    private double deltaX(int x, int y)
    {
        double result = 0.0;

        Color col1 = new Color(colorArray[x - 1][y]);
        Color col2 = new Color(colorArray[x + 1][y]);

        int redDiff = col2.getRed() - col1.getRed();
        int blueDiff = col2.getBlue() - col1.getBlue();
        int greenDiff = col2.getGreen() - col1.getGreen();

        result = redDiff * redDiff + blueDiff*blueDiff + greenDiff * greenDiff;

        return result;
    }

    private double deltaY(int x, int y)
    {
        double result = 0.0;

        Color row1 = new Color(colorArray[x][y - 1]);
        Color row2 = new Color(colorArray[x][y + 1]);

        int redDiff = row2.getRed() - row1.getRed();
        int blueDiff = row2.getBlue() - row1.getBlue();
        int greenDiff = row2.getGreen() - row1.getGreen();

        result = redDiff * redDiff + blueDiff*blueDiff + greenDiff * greenDiff;

        return result;
    }

    private int[][] transposeMatrix(int [][] m)
    {
        int[][] temp = new int[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }    

    private int getLeftIdx(int idx)
    {
        if (idx > 0)
            return idx - 1;
        else
            return Integer.MAX_VALUE;
    }

    private int getRightIdx(int idx)
    {
        if (idx < (picWidth - 1))
            return idx + 1;
        else
            return Integer.MAX_VALUE;
    }

    /*current picture*/
    public Picture picture()
    {
        Picture newPic = new Picture(colorArray.length, colorArray[0].length);
        for (int x = 0; x < colorArray.length; x++)
        {
            for (int y = 0; y < colorArray[0].length; y++)
            {
                newPic.set(x, y, new Color(colorArray[x][y]));
            }
        }
        return newPic;
    }

    /*width of current picture*/
    public int width()
    {
        return picWidth;
    }

    /*height of current picture*/
    public int height()
    {
        return picHeight;
    }

    /*energy of pixel at column x and row y*/
    public double energy(int x, int y)
    {
        if ((0 > x) || (x > (picWidth -1)) || (0 > y) || (y > (picHeight - 1)))
            throw new java.lang.IndexOutOfBoundsException();

        double result = 0.0;

        if ((0 == x) || (0 == y) || ((picWidth -1) == x) || ((picHeight -1) == y))
            result = 1000.0;
        else
        {
            result = Math.sqrt(deltaX(x, y) + deltaY(x, y));
        }

        return result;
    }

    /*sequence of indices for horizontal seam*/
    public int[] findHorizontalSeam()
    {
        int [] tab;
        int tempDim = picWidth;
        colorArray = transposeMatrix(colorArray);
        picWidth = picHeight;
        picHeight = tempDim;

        tab = findVerticalSeam();

        colorArray = transposeMatrix(colorArray);
        tempDim = picWidth;
        picWidth = picHeight;
        picHeight = tempDim;

        return tab;
    }

    /*sequence of indices for vertical seam*/
    public int[] findVerticalSeam()
    {
        double[][] energyArray = new double[picWidth][picHeight];
        double[][] distTo = new double[picWidth][picHeight];
        int [][] edgeTo = new int[picWidth][picHeight];
        int [] tab = new int[picHeight];
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MAX_VALUE;

        int xMinInRow = Integer.MAX_VALUE;
        double energyMinBot = Double.MAX_VALUE;

        for (int x = 0; x < picWidth; x++)
        {
            for (int y = 0; y < picHeight; y++)
            {
                if (0 == y)
                {
                    distTo[x][y] = 1000;
                }
                else
                {
                    distTo[x][y] = Double.MAX_VALUE;
                }
                edgeTo[x][y] = Integer.MAX_VALUE;
                energyArray[x][y] = energy(x, y);
            }
        }

        for (int x = 0; x < picWidth; x++)
        {
            xMin = x;
            xMax = x;
            for (int y = 0; y < picHeight - 1; y++)
            {
                for (int idx = xMin; idx <= xMax; idx++)
                {
                    int idxLeft = getLeftIdx(idx);
                    int idxMid = idx;
                    int idxRight = getRightIdx(idx);
                    double pathEnergy;

                    /*Middle idx*/
                    pathEnergy = distTo[idx][y] + energyArray[idxMid][y + 1];
                    if (pathEnergy < distTo[idxMid][y + 1])
                    {
                        distTo[idxMid][y + 1] = pathEnergy;
                        edgeTo[idxMid][y + 1] = idx;
                    }

                    /*Left idx*/
                    if (Integer.MAX_VALUE != idxLeft)
                    {
                        pathEnergy = distTo[idx][y] + energyArray[idxLeft][y + 1];
                        if (pathEnergy < distTo[idxLeft][y + 1])
                        {
                            distTo[idxLeft][y + 1] = pathEnergy;
                            edgeTo[idxLeft][y + 1] = idx;
                        }
                    }

                    /*Right idx*/
                    if (Integer.MAX_VALUE != idxRight)
                    {
                        pathEnergy = distTo[idx][y] + energyArray[idxRight][y + 1];
                        if (pathEnergy < distTo[idxRight][y + 1])
                        {
                            distTo[idxRight][y + 1] = pathEnergy;
                            edgeTo[idxRight][y + 1] = idx;
                        }
                    }
                }
                xMin = getLeftIdx(xMin);
                if (Integer.MAX_VALUE == xMin)
                    xMin = 0;

                xMax = getRightIdx(xMax);
                if (Integer.MAX_VALUE == xMax)
                    xMax = picWidth - 1;
            }
        }

        /*Find min value x idx in bottom row*/
        for (int x = 0; x < picWidth; x++)
        {
            if (distTo[x][picHeight - 1] < energyMinBot)
            {
                xMinInRow = x;
                energyMinBot = distTo[x][picHeight - 1];
            }
        }

        /*Find resulting seam*/
        tab[picHeight - 1] = xMinInRow;
        for (int y = picHeight - 1; y > 0; y--)
        {
            tab[y - 1] = edgeTo[xMinInRow][y];
            xMinInRow = edgeTo[xMinInRow][y];
        }

        return tab;
    }

    /*remove horizontal seam from current picture*/
    public void removeHorizontalSeam(int[] seam)
    {
        if (null == seam)
            throw new java.lang.NullPointerException();

        if (1 > picWidth || !horSeamValidation(seam))
            throw new java.lang.IllegalArgumentException();

        int [][] col = new int[picWidth][picHeight - 1];

        for (int x = 0; x < picWidth; x++)
        {
            System.arraycopy(colorArray[x], 0, col[x], 0, seam[x]);
            if (seam[x] < picHeight - 1)
            {
                System.arraycopy(colorArray[x], seam[x] + 1, col[x], seam[x], picHeight - seam[x] - 1);
            }
        }

        colorArray = col;
        picHeight--;
    }

    /*remove vertical seam from current picture*/
    public void removeVerticalSeam(int[] seam)
    {
        if (null == seam)
            throw new java.lang.NullPointerException();

        if (1 > picHeight || !verSeamValidation(seam))
            throw new java.lang.IllegalArgumentException();

        int tempDim = picWidth;
        colorArray = transposeMatrix(colorArray);
        picWidth = picHeight;
        picHeight = tempDim;

        removeHorizontalSeam(seam);

        colorArray = transposeMatrix(colorArray);
        tempDim = picWidth;
        picWidth = picHeight;
        picHeight = tempDim;
    }
}