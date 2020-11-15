/* *****************************************************************************
 *  Name:              Roman Zaiats
 *  Coursera User ID:  b38ab3d10530110bb13487a0a9254f25
 **************************************************************************** */

public class Percolation {
    private final int size;
    private final int[] grid1;

    private int virtualBottomSite;

    private int virtualTopSite;
    private int openSitesCount = 0;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        grid1 = new int[n * n + 2];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int index = i * n + j;
                grid1[index] = index;
            }
        }

        size = n;
        virtualTopSite = n * n;
        virtualBottomSite = n * n + 1;

        grid1[virtualTopSite] = virtualTopSite;
        grid1[virtualBottomSite] = virtualBottomSite;
    }

    public void open(int row, int column) {
        validate(row, column);

        int index = mapToOneDimensionArrayIndex(row, column);

        if (isOpen(index)) {
            return;
        }

        openSitesCount++;

        boolean isFirstRow = row == 1;
        boolean isFirstColumn = column == 1;
        boolean isLastRow = row == size;
        boolean isLastColumn = column == size;

        grid1[index] = -index;

        if (!isFirstRow) {
            int siteAboveIndex = index - size;

            if (isOpen(siteAboveIndex)) {
                union(index, siteAboveIndex);
            }
        }
        else {
            union(index, virtualTopSite);
        }

        if (!isLastRow) {
            int siteBelow = index + size;

            if (isOpen(siteBelow)) {
                union(index, siteBelow);
            }
        }
        else {
            union(index, virtualBottomSite);
        }

        if (!isFirstColumn) {
            int siteOnLeft = index - 1;

            if (isOpen(siteOnLeft)) {
                union(index, siteOnLeft);
            }
        }

        if (!isLastColumn) {
            int siteOnRight = index + 1;

            if (isOpen(siteOnRight)) {
                union(index, siteOnRight);
            }
        }
    }

    public boolean isOpen(int row, int column) {
        validate(row, column);

        return isOpen(mapToOneDimensionArrayIndex(row, column));
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

    private boolean isOpen(int index) {
        int value = grid1[index];

        if (value != index) {
            return true;
        }
        else {
            return false;
        }
    }

    private int findRoot(int index) {
        while (grid1[index] != index && grid1[index] != -index) {
            index = grid1[index];
        }

        return index;
    }

    private void union(int index1, int index2) {
        int root1 = findRoot(index1);
        int root2 = findRoot(index2);

        if (root1 == root2) {
            return;
        }

        //no weighted. simple quick union:
        grid1[root1] = Math.abs(root2);
        grid1[root2] = Math.abs(root2);
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
        if (row < 1 || col < 1) {
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
