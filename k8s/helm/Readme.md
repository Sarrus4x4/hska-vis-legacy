# Install mysql

Create the namespace if not already exists

```bash
kubectl create ns mysql
```

```bash
cd mysql/
helm3 upgrade --install -n mysql mysql .
```

Because of the persistent volume it may not be enough to just uninstall the helm chart if any changes to the database is made. To get a fresh install first uninstall the helm chart and then delete the persistent volume on the disk. Maybe this can somehow be avoided with `persistentVolumeReclaimPolicy: Reclaim`.

```bash
helm3 uninstall -n mysql mysql

cd /mnt/k8s/
sudo rm -r mysql
```

## Secret

```bash
kubectl create secret generic -n mysql mysql-credentials --from-literal=MYSQL_ROOT_PASSWORD='c8de110f37300a53a971749'
```

## Local images

To be able to use docker images in kubernetes they must be pushed to a registry. Microk8s provides a registry addon (Minikube seems to also have on). Docker images can be pushed to this registry. By tagging the image with `localhost:32000/`, Kubernetes tries to pull the image from this build-in registry.

```bash
microk8s enable registry

docker build -t localhost:32000/hska-vis-mysql:0.1.0 -f docker/DockerfileMySQL .
docker push localhost:32000/hska-vis-mysql:0.1.0
```