import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;

public class main {

	public static void main(String[] args) {



		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/examen", "root", "1234");
			conexion.createStatement();
			Statement sentencia = conexion.createStatement();
			// Ejecutar la consulta
			ResultSet resultado = sentencia.executeQuery("SELECT * FROM cliente where id=00103228");

			// Procesar el resultado
			while (resultado.next()) {
				int id = resultado.getInt("id");
				String nombre = resultado.getString("nombre");
				String apellido = resultado.getString("apellido");

				System.out.println("ID: " + id + ", Nombre: " + nombre + ", Apellido: " + apellido);
			}
			ResultSet resultado2 = sentencia.executeQuery("SELECT * FROM prestamo where Id_Cliente=00103228 ");

			// Procesar el resultado
			while (resultado2.next()) {
				int plazo;
				int dias_ano_comercial = 360;
				float tasa_interes = (float) .065;
				float interes;
				float pago;
				float tasa_iva = (float) .16;
				float iva;
		

				
				Date fecha = resultado2.getDate("fecha");
				float monto = resultado2.getFloat("monto");
			
				plazo = plazo(fecha);

				interes = Interes(monto, plazo, tasa_interes, dias_ano_comercial);
				iva = iva(interes, tasa_iva);
				pago = pago(monto, interes, iva);
				System.out.print("\n plazo: " + plazo + " Tasa de interes " + tasa_interes + " monto: " + monto
						+ " interes:  " + interes + " IVA: " + iva + " pago: " + pago);

			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static int plazo(Date prestamo_fecha) {

		Date fecha_actual = new Date(121, 2, 15);

		int dias = (int) ((fecha_actual.getTime() - prestamo_fecha.getTime()) / 86400000);
		return dias;

	}

	public static float Interes(float monto, int plazo, float tasa_interes, int dias_ano_comercial) {
		float interes;
		interes = (monto * plazo * tasa_interes) / dias_ano_comercial;
		double num = interes;
		double roundedNum = Math.round(num * 100.0) / 100.0;

		return (float) roundedNum;

	}

	public static float iva(float interes, float tasa_iva) {
		float iva;
		iva = interes * tasa_iva;
		double num = iva;
		double roundedNum = Math.round(num * 100.0) / 100.0;
		return (float) roundedNum;
	}

	public static float pago(float monto, float interes, float iva) {
		float pago;
		pago = monto + interes + iva;
		return pago;
	}

}
