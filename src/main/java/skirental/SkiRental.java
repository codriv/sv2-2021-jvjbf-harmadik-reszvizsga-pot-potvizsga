package skirental;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class SkiRental {

    private Map<String, Equipment> rentals = new TreeMap<>(Comparator.naturalOrder());

    public Map<String, Equipment> getRentals() {
        return rentals;
    }

    public void loadFromFile(Path path) {
        String[] pathParts = path.toString().split("/");
        String fileName = pathParts[pathParts.length - 1];
        try (Scanner scanner = new Scanner(path)){
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                fillRentals(scanner.nextLine());
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + fileName);
        }
    }

    private void fillRentals(String line) {
        String[] parts = line.split(";");
        String name = parts[0];
        String[] equipments = parts[1].split(" ");
        int skiSize = Integer.parseInt(equipments[0]);
        int bootSize = Integer.parseInt(equipments[1]);
        rentals.put(name, new Equipment(skiSize, bootSize));
    }

    public List<String> listChildren() {
        return rentals.keySet().stream()
                .filter(name -> isChildSki(rentals.get(name).getSizeOfSkis()) && isChildBoot(rentals.get(name).getSizeOfBoot()))
                .toList();
    }

    private boolean isChildSki(int size) {
        return size <= 120 && size != 0;
    }

    private boolean isChildBoot(int size) {
        return size <= 37 && size != 0;
    }

    public String getNameOfPeopleWithBiggestFoot() {
        return rentals.keySet().stream()
                .filter(name -> rentals.get(name).getSizeOfSkis() != 0)
                .max((o1, o2) -> {
                    int difference = rentals.get(o1).getSizeOfBoot() - rentals.get(o2).getSizeOfBoot();
                    return difference == 0 ? o2.compareTo(o1) : difference;
                })
                .orElseThrow(() -> new IllegalStateException("Client not found!"));
    }
}
