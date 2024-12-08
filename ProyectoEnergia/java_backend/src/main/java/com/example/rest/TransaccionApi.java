package com.example.rest;

import java.util.HashMap;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import com.example.rest.controller.dao.services.TransaccionServices;
import com.example.rest.models.Transaccion;

@Path("transaccion")
public class TransaccionApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTransacciones() {
        HashMap<String, Object> map = new HashMap<>();
        TransaccionServices ts = new TransaccionServices();
        map.put("msg", "OK");
        map.put("data", ts.listAll().toArray());
        if (ts.listAll().isEmpty()) {
            map.put("data", new Object[]{});
        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransaccion(@PathParam("id") int id) {
        HashMap<String, Object> map = new HashMap<>();
        TransaccionServices ts = new TransaccionServices();
        try {
            Transaccion transaccion = ts.get(id);
            if (transaccion == null) {
                map.put("msg", "Error");
                map.put("data", "No existe la transacción con ese identificador");
                return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
            }
            map.put("msg", "OK");
            map.put("data", transaccion);
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveTransaccion(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            TransaccionServices ts = new TransaccionServices();
            ts.getTransaccion().setTipoOperacion(map.get("tipoOperacion").toString());
            ts.getTransaccion().setNombreProyecto(map.get("nombreProyecto").toString());
            ts.getTransaccion().setFechaHora(java.time.LocalDateTime.parse(map.get("fechaHora").toString()));
            ts.save();
            res.put("msg", "OK");
            res.put("data", "Transacción registrada correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTransaccion(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            TransaccionServices ts = new TransaccionServices();
            ts.setTransaccion(ts.get(Integer.parseInt(map.get("id").toString())));
            if (ts.getTransaccion() == null) {
                res.put("msg", "Error");
                res.put("data", "No existe la transacción con ese identificador");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            ts.getTransaccion().setTipoOperacion(map.get("tipoOperacion").toString());
            ts.getTransaccion().setNombreProyecto(map.get("nombreProyecto").toString());
            ts.getTransaccion().setFechaHora(java.time.LocalDateTime.parse(map.get("fechaHora").toString()));
            ts.update();
            res.put("msg", "OK");
            res.put("data", "Transacción actualizada correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTransaccion(@PathParam("id") int id) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            TransaccionServices ts = new TransaccionServices();
            ts.delete(id);
            map.put("msg", "OK");
            map.put("data", "Transacción eliminada correctamente");
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }
}
