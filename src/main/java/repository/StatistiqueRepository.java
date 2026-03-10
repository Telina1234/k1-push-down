package repository;

import model.CoutReparation;
import model.MecanicienMoinsRentable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatistiqueRepository {

    private Connection connection;

    public StatistiqueRepository(Connection connection) {
        this.connection = connection;
    }

    public List<CoutReparation> getCoutReparationParMecanicien() throws SQLException {

        String sql = """
            SELECT
                m.marque,
                m.nom AS nom_mecanicien,
                COALESCE(SUM(r.cout),0) AS cout_reparation
            FROM Mecanicien m
            LEFT JOIN Reparation r
                ON m.id = r.id_mecanicien
            GROUP BY m.marque, m.nom
            ORDER BY m.nom
            """;

        List<CoutReparation> result = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            CoutReparation stat = new CoutReparation();

            stat.setMarque(rs.getString("marque"));
            stat.setNomMecanicien(rs.getString("nom_mecanicien"));
            stat.setCoutReparation(rs.getDouble("cout_reparation"));

            result.add(stat);
        }

        return result;
    }

    public MecanicienMoinsRentable getMecanicienMoinsRentable() throws SQLException {

        String sql = """
            SELECT
                m.nom AS mecanicien,
                COALESCE(SUM(r.cout),0) AS cout_reparation_rapporte
            FROM Mecanicien m
            LEFT JOIN Reparation r
                ON m.id = r.id_mecanicien
            GROUP BY m.nom
            ORDER BY cout_reparation_rapporte ASC
            LIMIT 1
            """;

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        MecanicienMoinsRentable result = null;

        if (rs.next()) {

            result = new MecanicienMoinsRentable();
            result.setMecanicien(rs.getString("mecanicien"));
            result.setCoutRapporte(rs.getDouble("cout_reparation_rapporte"));
        }

        return result;
    }
}