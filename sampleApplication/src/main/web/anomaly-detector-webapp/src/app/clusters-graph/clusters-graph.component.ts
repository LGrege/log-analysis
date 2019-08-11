import {Component, OnInit} from '@angular/core';
import {ClusterService} from "../cluster.service";
import {Cluster} from "../cluster";
import {LoggingEvent} from "../logging.event";
import {Edge, Node, ClusterNode} from '@swimlane/ngx-graph';
import {Subject} from "rxjs";

@Component({
  selector: 'app-clusters-graph',
  templateUrl: './clusters-graph.component.html',
  styleUrls: ['./clusters-graph.component.css']
})
export class ClustersGraphComponent implements OnInit {

  center$: Subject<boolean> = new Subject();
  zoomToFit$: Subject<boolean> = new Subject();

  nodes: Node[] = [];
  clusters: ClusterNode[] = [];
  links: Edge[] = [];

  constructor(private clusterService: ClusterService) {
  }

  ngOnInit() {
    this.clusterService.currentCluster.subscribe(
      cluster => this.initializeClusters(cluster))
  }

  initializeClusters(cluster: Cluster): void {
    if (cluster != null) {
      this.resetData();
      const clusterID = this.createNewCluster();
      cluster.events.forEach((event, eventCounter) => {
        const nodeID = `N${eventCounter}`;
        this.createNewNode(nodeID, event);
        this.createNewLink(clusterID, nodeID, event.logger);
      });
      this.zoomToFit$.next(true);
      this.center$.next(true);
    }
  };

  createNewCluster(): string {
    const clusterID = `selectedCluster`;
    this.nodes.push({id: clusterID, label: clusterID});
    return clusterID;
  }

  createNewNode(nodeID: string, event: LoggingEvent) {
    this.nodes.push({
      id: nodeID,
      label: event.message
    });
  }

  createNewLink(clusterID: string, nodeID: string, label: string) {
    const linkID = `L-${clusterID}-${nodeID}`;
    this.links.push({
      id: linkID,
      source: nodeID,
      target: clusterID,
      label: label
    });
  }

  resetData() {
    this.clusters = [];
    this.nodes = [];
    this.links = [];
  }
}
