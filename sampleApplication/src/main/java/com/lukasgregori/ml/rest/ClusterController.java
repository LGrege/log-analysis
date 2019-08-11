/*********************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Lukas Gregori
 * contact@lukasgregori.com
 * www.lukasgregori.com
 *
 * (c) 2018 by Lukas Gregori
 *********************************************************************/

package com.lukasgregori.ml.rest;

import com.lukasgregori.ml.clustering.impl.ClusteringContext;
import com.lukasgregori.ml.rest.dao.SOClusterDAO;
import com.lukasgregori.ml.rest.util.SOClusterDAOFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lukas Gregori
 */
@RestController
@RequestMapping("/cluster")
public class ClusterController {

    @Resource
    private ClusteringContext clusteringContext;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public List<SOClusterDAO> getAllClusters() {
        return clusteringContext.getAllClusters().stream()
                .map(SOClusterDAOFactory::createCluster)
                .collect(Collectors.toList());
    }
}
