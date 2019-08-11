import { TestBed } from '@angular/core/testing';

import { ClusterService } from './cluster.service';

describe('ClusterService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ClusterService = TestBed.get(ClusterService);
    expect(service).toBeTruthy();
  });
});
