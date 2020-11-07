package main.java.model;

import java.util.Random;

public class GenerationValues {

    static public double generationNorm(double sigma, int mat) {
        return new Random().nextGaussian() * sigma + mat;
    }

    static public double generationUniform(int startBoundary, int endBoundary) {
        return startBoundary + new Random().nextDouble() * (endBoundary - startBoundary);
    }

    static public double generationExponential(double intensity) {
        return Math.log(1 - new Random().nextDouble()) / -intensity;
    }
}
