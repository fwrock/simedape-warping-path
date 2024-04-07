package org.simedape.wp.metric.dtw;

import org.simedape.wp.domain.DynamicTimeWarpingResult;

import java.util.ArrayList;
import java.util.List;

public class DynamicTimeWarping {

    public DynamicTimeWarpingResult distance(double[] s1, double[] s2) {
        int n = s1.length;
        int m = s2.length;
        double[][] DTW = new double[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            DTW[i][0] = Double.POSITIVE_INFINITY;
        }

        for (int i = 1; i <= m; i++) {
            DTW[0][i] = Double.POSITIVE_INFINITY;
        }

        DTW[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                double cost = Math.abs(s1[i - 1] - s2[j - 1]);
                DTW[i][j] = cost + Math.min(DTW[i - 1][j], Math.min(DTW[i][j - 1], DTW[i - 1][j - 1]));
            }
        }

        return new DynamicTimeWarpingResult(DTW[n][m], bestPath(DTW));
    }

    public DynamicTimeWarpingResult distance(double[] seqA, double[] seqB, int r) {
        int n = seqA.length;
        int m = seqB.length;
        double[][] dtw = new double[n + 1][m + 1];
        dtw[0][0] = 0;

        double[] lowerBound = new double[n];
        double[] upperBound = new double[n];

        for (int i = 0; i < n; i++) {
            lowerBound[i] = Double.POSITIVE_INFINITY;
            upperBound[i] = Double.NEGATIVE_INFINITY;
            for (int j = Math.max(0, i - r); j < Math.min(m, i + r); j++) {
                double value = seqB[j];
                lowerBound[i] = Math.min(lowerBound[i], value);
                upperBound[i] = Math.max(upperBound[i], value);
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = Math.max(1, i - r); j <= Math.min(m, i + r); j++) {
                double cost = Math.abs(seqA[i - 1] - seqB[j - 1]);
                double adjCost = cost;
                if (cost > upperBound[i - 1]) {
                    adjCost = Math.abs(cost - upperBound[i - 1]);
                } else if (cost < lowerBound[i - 1]) {
                    adjCost = Math.abs(cost - lowerBound[i - 1]);
                }
                dtw[i][j] = adjCost + Math.min(Math.min(dtw[i - 1][j], dtw[i][j - 1]), dtw[i - 1][j - 1]);
            }
        }

        return new DynamicTimeWarpingResult(dtw[n][m], bestPath(dtw));
    }

    private List<int[]> bestPath(double[][] dtw) {
        int n = dtw.length;
        int m = dtw[0].length;
        List<int[]> path = new ArrayList<>();
        int i = n - 1;
        int j = m - 1;
        while (i > 0 || j > 0) {
            path.add(0, new int[]{i, j});
            double minPrev = Math.min(Math.min(dtw[i - 1][j], dtw[i][j - 1]), dtw[i - 1][j - 1]);
            if (minPrev == dtw[i - 1][j - 1]) {
                i--;
                j--;
            } else if (minPrev == dtw[i][j - 1]) {
                j--;
            } else {
                i--;
            }
        }
        path.add(0, new int[]{0, 0});
        return path;
    }
}
