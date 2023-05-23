package src.usecases;

import src.interceptors.LoggedInvocation;
import src.services.NameGenerator;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SessionScoped
@Named
public class GenerateName implements Serializable {
    @Inject
    NameGenerator nameGenerator;

    private CompletableFuture<String> nameGeneratorTask = null;

    @LoggedInvocation
    public String generateNewName() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        nameGeneratorTask = CompletableFuture.supplyAsync(() -> nameGenerator.generateName());
        return  "/doctorDetails.xhtml?faces-redirect=true&doctorId=" + requestParameters.get("doctorId");
    }

    public boolean isNameGenerationRunning() {
        return nameGeneratorTask != null && !nameGeneratorTask.isDone();
    }

    public String getNameGenerationStatus() throws ExecutionException, InterruptedException {
        if (nameGeneratorTask == null) {
            return null;
        } else if (isNameGenerationRunning()) {
            return "Name generation in progress";
        }
        return "Suggested name: " + nameGeneratorTask.get();
    }
}
