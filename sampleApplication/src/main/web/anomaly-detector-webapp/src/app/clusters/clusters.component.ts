import {Component, OnInit} from '@angular/core';
import {ClusterService} from '../cluster.service';
import {Cluster} from '../cluster';
import {NgbPanelChangeEvent} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-clusters',
  templateUrl: './clusters.component.html',
  styleUrls: ['./clusters.component.css']
})
export class ClustersComponent implements OnInit {

  clusters: Cluster[];

  constructor(private clusterService: ClusterService) {
  }

  ngOnInit() {
    this.getClusters();
  }

  getClusters(): void {
    this.clusterService.getClusters()
      .subscribe(clusters =>
        this.clusters = clusters);
  }

  onClusterSelected($event: NgbPanelChangeEvent) {
    const selectedPanelID = $event.panelId;
    const selectedCluster = this.clusters[selectedPanelID];
    this.clusterService.setCurrentlySelectedCluster(selectedCluster);
  }
}
