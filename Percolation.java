/* *****************************************************************************
 *  Name:              Roman Zaiats
 *  Coursera User ID:  b38ab3d10530110bb13487a0a9254f25
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size;
    private final boolean[] isOpenedArray;
    private final int virtualBottomSite;
    private final int virtualTopSite;
    private final WeightedQuickUnionUF weightedQuickUnionUF;

    private int openSitesCount = 0;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        isOpenedArray = new boolean[n * n];

        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);

        size = n;
        virtualTopSite = n * n;
        virtualBottomSite = n * n + 1;
    }

    public void open(int row, int column) {
        validate(row, column);

        int index = mapToOneDimensionArrayIndex(row, column);

        if (isOpenedArray[index]) {
            return;
        }

        openSitesCount++;
        isOpenedArray[index] = true;

        boolean isFirstRow = row == 1;
        boolean isFirstColumn = column == 1;
        boolean isLastRow = row == size;
        boolean isLastColumn = column == size;

        if (!isFirstRow) {
            int siteAboveIndex = index - size;

            if (isOpenedArray[siteAboveIndex]) {
                union(index, siteAboveIndex);
            }
        }
        else {
            union(index, virtualTopSite);
        }

        if (!isLastRow) {
            int siteBelow = index + size;

            if (isOpenedArray[siteBelow]) {
                union(index, siteBelow);
            }
        }
        else {
            union(index, virtualBottomSite);
        }

        if (!isFirstColumn) {
            int siteOnLeft = index - 1;

            if (isOpenedArray[siteOnLeft]) {
                union(index, siteOnLeft);
            }
        }

        if (!isLastColumn) {
            int siteOnRight = index + 1;

            if (isOpenedArray[siteOnRight]) {
                union(index, siteOnRight);
            }
        }
    }

    public boolean isOpen(int row, int column) {
        validate(row, column);

        return isOpenedArray[mapToOneDimensionArrayIndex(row, column)];
    }

    public boolean isFull(int row, int column) {
        validate(row, column);

        int index = mapToOneDimensionArrayIndex(row, column);

        return isConnected(index, virtualTopSite);
    }

    public boolean percolates() {
        return isConnected(virtualTopSite, virtualBottomSite);
    }

    private int mapToOneDimensionArrayIndex(int row, int column) {
        column = normalize(column);
        row = normalize(row);

        return row * size + column;
    }

    private int findRoot(int index) {
        return weightedQuickUnionUF.find(index);
    }

    private void union(int index1, int index2) {
        weightedQuickUnionUF.union(index1, index2);
    }

    private boolean isConnected(int index1, int index2) {
        int root1 = findRoot(index1);
        int root2 = findRoot(index2);

        return root1 == root2;
    }

    public int numberOfOpenSites() {
        return openSitesCount;
    }

    private void validate(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return normalized (mapped from index starting at 1 to index starting at 0)
     */
    private int normalize(int value) {
        return value - 1;
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(6);

        percolation.open(1, 3);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(4, 3);
        percolation.open(5, 3);

        System.out.println(percolation.percolates());
    }
}
