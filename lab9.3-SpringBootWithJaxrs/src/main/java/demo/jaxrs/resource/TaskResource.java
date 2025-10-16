package demo.jaxrs.resource;

import demo.jaxrs.model.Task;
import demo.jaxrs.repo.TaskRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    @Inject
    private TaskRepository taskRepository;

    @GET
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(value -> Response.ok(value).build())
                   .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response create(Task task) {
        Task saved = taskRepository.save(task);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Task updatedTask) {
        return taskRepository.findById(id)
            .map(existing -> {
                existing.setDescription(updatedTask.getDescription());
                taskRepository.save(existing);
                return Response.ok(existing).build();
            })
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
