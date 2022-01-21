package com.myorg;

import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.StackProps;

import java.util.Arrays;

public class AwsCdkApp {
    public static void main(final String[] args) {
        App app = new App();

        VpcStack vpcStack = new VpcStack(app, "Vpc");

        ClusterStack clusterStack = new ClusterStack(app, "Cluster", vpcStack.getVpc());
        clusterStack.addDependency(vpcStack);

        RdsStack rdsStack = new RdsStack(app, "Rds", vpcStack.getVpc());
        clusterStack.addDependency(vpcStack);

        SnsStack snsStack = new SnsStack(app, "Sns");

        Service01tack service01tack = new Service01tack(app, "Service01"
                , clusterStack.getCluster(), snsStack.getProductEventsTopic());
        service01tack.addDependency(clusterStack);
        service01tack.addDependency(rdsStack);
        service01tack.addDependency(snsStack);

        app.synth();
    }
}
