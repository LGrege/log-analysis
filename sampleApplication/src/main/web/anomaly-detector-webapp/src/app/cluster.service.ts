import {Injectable} from '@angular/core';
import {Cluster} from './cluster';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";

const endpoint = 'http://localhost:8080/';

@Injectable({
  providedIn: 'root'
})

export class ClusterService {

  private currentClusterSource = new BehaviorSubject(null);
  currentCluster = this.currentClusterSource.asObservable();

  constructor(private http: HttpClient) {
  }

  setCurrentlySelectedCluster(cluster: Cluster) {
    this.currentClusterSource.next(cluster)
  }

  getClusters(): Observable<Cluster[]> {
    return this.http.get(endpoint + 'cluster').pipe(
      map(data => ClusterService.sortClusters(<Cluster[]>data)));
  }

  static sortClusters(unsortedClusters: Cluster[]): Cluster[] {
    return unsortedClusters.sort((c1, c2) =>
      ClusterService.compareClusters(c1, c2));
  }

  static compareClusters(c1: Cluster, c2: Cluster): number {
    return c2.weight - c1.weight;
  }
}
