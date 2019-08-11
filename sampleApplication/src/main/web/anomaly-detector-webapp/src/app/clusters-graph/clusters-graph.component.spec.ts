import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClustersGraphComponent } from './clusters-graph.component';

describe('ClustersGraphComponent', () => {
  let component: ClustersGraphComponent;
  let fixture: ComponentFixture<ClustersGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClustersGraphComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClustersGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
