package dbInterface.command;

import java.util.ArrayList;
import java.util.List;

public class DbOperationExecutor {

    private List<IDbOperation> dbOperationList = new ArrayList<>();

    public DbOperationResult executeOperation(IDbOperation dbOperation) {
        dbOperationList.add(dbOperation);
        return dbOperation.execute();
    }
}
