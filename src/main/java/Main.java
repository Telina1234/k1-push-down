import db.DBConnection;
import repository.StatistiqueRepository;
import model.CoutReparation;
import model.MecanicienMoinsRentable;

import java.sql.Connection;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {

            Connection connection = new DBConnection().getConnection();

            StatistiqueRepository repo = new StatistiqueRepository(connection);

            List<CoutReparation> stats = repo.getCoutReparationParMecanicien();

            for (CoutReparation s : stats) {
                System.out.println(
                        s.getMarque() + " | " +
                                s.getNomMecanicien() + " | " +
                                s.getCoutReparation()
                );
            }

            MecanicienMoinsRentable moinsRentable = repo.getMecanicienMoinsRentable();

            System.out.println(
                    "\nMecanicien le moins rentable : "
                            + moinsRentable.getMecanicien()
                            + " -> "
                            + moinsRentable.getCoutRapporte()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}