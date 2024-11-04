package lt.vu.usecases;

import lt.vu.interceptors.LoggedInvocation;
import lt.vu.services.AsyncCalculation;

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
public class generateAsync implements Serializable {
    @Inject
    AsyncCalculation asyncCalculation;

    private CompletableFuture<Integer> asyncCalculationTask = null;

    @LoggedInvocation
    public String generate() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        asyncCalculationTask = CompletableFuture.supplyAsync(() -> asyncCalculation.calculate());

        return  "/memberDetails.xhtml?faces-redirect=true&memberId=" + requestParameters.get("memberId");
    }

    public boolean isAsyncRunning() {
        return asyncCalculationTask != null && !asyncCalculationTask.isDone();
    }

    public String getAsyncStatus() throws ExecutionException, InterruptedException {
        if (asyncCalculationTask == null) {
            return null;
        } else if (isAsyncRunning()) {
            return "Async in progress";
        }
        return "number: " + asyncCalculationTask.get();
    }
}
