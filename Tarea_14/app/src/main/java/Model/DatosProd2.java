package Model;

public class DatosProd2 {
    private String Uid;
    private String Codigo;
    private String Producto;
    private String Stock;
    private String Costo;
    private String Venta;

    public DatosProd2() {
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public String getCosto() {
        return Costo;
    }

    public void setCosto(String costo) {
        Costo = costo;
    }

    public String getVenta() {
        return Venta;
    }

    public void setVenta(String venta) {
        Venta = venta;
    }

    @Override
    public String toString() {
        return  "COD. Producto: "+Codigo+"\n"
                +"Nombre de Producto: "+Producto + "\n"
                +"Stock: "+ Stock+"\n"
                +"Precio Venta: "+Venta;
    }
}
