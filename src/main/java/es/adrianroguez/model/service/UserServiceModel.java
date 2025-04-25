package es.adrianroguez.model.service;

import es.adrianroguez.model.UserModel;
import es.adrianroguez.model.abstracts.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceModel extends Conexion {

    /**
     * Constructor que llama al constructor de la clase Conexion.
     */
    public UserServiceModel() {
        super();
    }

    /**
     * Metodo para registrar un nuevo usuario en la base de datos.
     * 
     * @param usuario El objeto de tipo UserModel que contiene los datos del
     *                usuario.
     * @return true si el registro fue exitoso, false en caso contrario.
     */
    public boolean registrarUsuario(UserModel usuario) {
        String sql = "INSERT INTO users (user, email, password) VALUES (?, ?, ?)";

        try (Connection conn = conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getUser());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getPassword());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Metodo para eliminar un usuario basado en su correo electronico.
     * 
     * @param email El correo electronico del usuario que se quiere eliminar.
     * @return true si el usuario fue eliminado correctamente, false en caso
     *         contrario.
     */
    public boolean eliminarUsuarioPorEmail(String email) {
        String sql = "DELETE FROM users WHERE email = ?";

        try (Connection conn = conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Metodo para actualizar los datos de un usuario.
     * 
     * @param usuario El objeto de tipo UserModel que contiene los nuevos datos del
     *                usuario.
     * @return true si la actualizacion fue exitosa, false en caso contrario.
     */
    public boolean actualizarUsuario(UserModel usuario) {
        String sql = "UPDATE users SET user = ?, password = ? WHERE email = ?";

        try (Connection conn = conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getUser());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getEmail());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Metodo para listar todos los usuarios de la base de datos.
     * 
     * @return Una lista de objetos UserModel con los datos de todos los usuarios.
     */
    public List<UserModel> listarUsuarios() {
        List<UserModel> lista = new ArrayList<>();
        String sql = "SELECT user, email, password FROM users";

        try (Connection conn = conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UserModel u = new UserModel();
                u.setUser(rs.getString("user"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                lista.add(u);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Metodo para verificar si un usuario con el correo especificado ya existe.
     * 
     * @param email El correo electronico del usuario a verificar.
     * @return true si el usuario existe, false si no existe.
     */
    public boolean existeUsuario(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ?";

        try (Connection conn = conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.err.println("Error al verificar existencia del usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Metodo para obtener un usuario con las credenciales especificadas.
     * 
     * @param user     El nombre de usuario a buscar.
     * @param password La contrasena del usuario a verificar.
     * @return Un objeto UserModel con los datos del usuario si las credenciales son
     *         correctas, null si no existe el usuario.
     */
    public UserModel obtenerUsuarioPorCredenciales(String user, String password) {
        String sql = "SELECT * FROM users WHERE user = ? AND password = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new UserModel(rs.getString("user"), rs.getString("email"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
