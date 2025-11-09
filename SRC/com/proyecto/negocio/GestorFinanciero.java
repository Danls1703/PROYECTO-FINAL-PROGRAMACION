package com.proyecto.negocio;

import com.proyecto.model.Atleta;

/**
 * Clase de lógica de negocio para gestionar cálculos financieros.
 * Utiliza propiedades de Atleta que ya están disponibles en el DAO.
 */
public class GestorFinanciero {

    private static final double PAGO_BASE_MENSUAL = 500.00;
    private static final double INCENTIVO_MARCA_PERSONAL = 100.00; 

    /**
     * Calcula el pago mensual basado en las características del atleta.
     */
    public double calcularPagoMensual(Atleta atleta) {
        double pago = PAGO_BASE_MENSUAL;

        // Bonificación por Mejor Marca Personal (> 0)
        if (atleta.getMejorMarcaPersonal() > 0) {
            pago += INCENTIVO_MARCA_PERSONAL;
            System.out.println("    - Bonificación por mejor marca personal: $" + INCENTIVO_MARCA_PERSONAL);
        }

        // Ajuste por Atleta Extranjero
        if (atleta.isEsExtranjero()) {
            pago *= 1.10; // 10% adicional
            System.out.println("    - Ajuste por ser extranjero (10%): Sí");
        } else {
            System.out.println("    - Ajuste por ser extranjero (10%): No");
        }
        
        // Descuento por Edad (mayor de 30 años)
        if (atleta.getEdad() > 30) {
            pago -= 50.00;
            System.out.println("    - Descuento por edad (>30): $50.00");
        } else {
            System.out.println("    - Descuento por edad (>30): No");
        }

        return pago;
    }
}