package com.smartjob.bci.ejercicio.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SistemaUtil {

    private static Logger logger = LogManager.getLogger(SistemaUtil.class);


    public static final Character ESPACIO = ' ';
    
    private static Environment environment;

    @Autowired
    public SistemaUtil(Environment env) {
        environment = env;
    }

    public static Boolean esCadenaValidaParaBusquedas(String cadena) {
        return (esNoNulo(cadena) && !cadena.isEmpty());
    }

    public static Boolean esNoNulo(Object o) {
        return o != null;
    }

    public static Boolean esCadenaVacia(String cadena) {
        return cadena.trim().length() <= 0;
    }

    public static Boolean esNoNuloVerdadero(Object o) {
        return esNoNulo(o) && (boolean) o;
    }

    public static Boolean esNulo(Object o) {
        return o == null;
    }

    public static Boolean sonNoNulos(Object... obs) {
        for (Object o : obs) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean esNumero(String cadena) {
        boolean isDecimal = false;
        if (cadena == null || cadena.isEmpty()) {
            return false;
        }
        int i = 0;
        if (cadena.charAt(0) == '-') {
            if (cadena.length() > 1) {
                i++;
            } else {
                return false;
            }
        }
        for (; i < cadena.length(); i++) {
            if (!Character.isDigit(cadena.charAt(i))) {
                if (!isDecimal && (cadena.charAt(i) != '.' || cadena.charAt(i) != ',')) {
                    isDecimal = true;
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    public static Timestamp obtenerFechaHoraActual() {
        java.util.Date date = new java.util.Date();
        Timestamp timeStampTransaction = new Timestamp(date.getTime());
        return timeStampTransaction;
    }

    public static Timestamp obtenerFechaActual() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        return new Timestamp(date.getTime().getTime());
    }

    public static Date obtenerFechaActualDate() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        return new Date(date.getTime().getTime());
    }

    public static String obtenerFechaComoString(Date fecha) {
        String formato = "yyyy/MM/dd"; // Si no pasan el formato como parámetro, le asignamos uno por defecto
        return obtenerFechaComoString(fecha, formato);
    }

    public static String obtenerFechaComoString(Date fecha, String formato) {
        String fechaString;
        SimpleDateFormat sdf;

        sdf = new SimpleDateFormat(formato);
        fechaString = sdf.format(fecha);

        return fechaString;
    }

    public static Integer obtenerAnioIntDeFecha(Timestamp fecha) {
        Calendar c;
        c = Calendar.getInstance();
        c.setTimeInMillis(fecha.getTime());
        return c.get(Calendar.YEAR);
    }

    public static Integer obtenerMesDeFecha(Timestamp fecha) {
        Calendar c;
        c = Calendar.getInstance();
        c.setTimeInMillis(fecha.getTime());
        return c.get(Calendar.MONTH) + 1;
    }


    public static Integer obtenerAnioActual() {
        Calendar calendar;
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static Integer obtenerMesActual() {
        Calendar calendar;
        calendar = Calendar.getInstance();
        return (calendar.get(Calendar.MONTH) + 1);
    }

    public static Date obtenerInicioDia(Date fecha) {
        Calendar cal;
        cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        return new Timestamp(cal.getTimeInMillis());
    }

    public static <E extends Object> E buscarObjetoEnLista(List<E> lista, E valor) {
        Optional<E> busqueda = lista.stream().filter(p -> p.equals(valor)).findFirst();
        return busqueda.isPresent() ? busqueda.get() : null;
    }

    public static Boolean esCadenaValida(String cadena) {
        return (cadena != null && !cadena.isEmpty());
    }

    public static Boolean listaEstaVacia(List lista) {
        return !esNoNulo(lista) || lista.isEmpty();
    }

    public static String formatMoneda(BigDecimal moneda) {
        String respuesta = "";
        if (moneda != null) {
            DecimalFormat twoPlaces = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
            respuesta = twoPlaces.format(moneda);
        }
        return respuesta;
    }

    public static String obtenerFechaHoraActualFormato() {
        Timestamp ahora = obtenerFechaHoraActual();
        SimpleDateFormat format;
        format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(new Date(ahora.getTime()));
    }

    public static boolean validarEmail(String email) {
        String regex = environment.getProperty("regex.email");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validarPassword(String password) {
        String regex = environment.getProperty("regex.password");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
