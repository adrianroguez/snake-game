package es.adrianroguez.session;

import es.adrianroguez.model.UserModel;

public class Session {
    private static UserModel currentUser;

    /**
     * Establece el usuario actual para la sesion.
     * 
     * @param user El usuario que se quiere establecer como actual
     */
    public static void setCurrentUser(UserModel user) {
        currentUser = user;
    }

    /**
     * Obtiene el usuario actual de la sesion.
     * 
     * @return El usuario actual o null si no hay uno
     */
    public static UserModel getCurrentUser() {
        return currentUser;
    }

    /**
     * Limpia la sesion actual, eliminando el usuario guardado.
     */
    public static void clear() {
        currentUser = null;
    }
}
