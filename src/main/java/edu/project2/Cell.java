package edu.project2;

import java.util.List;

public record Cell(List<Side> restrictedFrom, Position position) {
}
