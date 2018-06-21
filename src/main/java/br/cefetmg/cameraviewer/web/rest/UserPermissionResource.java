package br.cefetmg.cameraviewer.web.rest;

import br.cefetmg.cameraviewer.security.AuthoritiesConstants;
import com.codahale.metrics.annotation.Timed;
import br.cefetmg.cameraviewer.domain.UserPermission;

import br.cefetmg.cameraviewer.repository.UserPermissionRepository;
import br.cefetmg.cameraviewer.web.rest.errors.BadRequestAlertException;
import br.cefetmg.cameraviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserPermission.
 */
@RestController
@RequestMapping("/api")
public class UserPermissionResource {

    private final Logger log = LoggerFactory.getLogger(UserPermissionResource.class);

    private static final String ENTITY_NAME = "userPermission";

    private final UserPermissionRepository userPermissionRepository;

    public UserPermissionResource(UserPermissionRepository userPermissionRepository) {
        this.userPermissionRepository = userPermissionRepository;
    }

    /**
     * POST  /user-permissions : Create a new userPermission.
     *
     * @param userPermission the userPermission to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userPermission, or with status 400 (Bad Request) if the userPermission has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-permissions")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserPermission> createUserPermission(@RequestBody UserPermission userPermission) throws URISyntaxException {
        log.debug("REST request to save UserPermission : {}", userPermission);
        if (userPermission.getId() != null) {
            throw new BadRequestAlertException("A new userPermission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPermission result = userPermissionRepository.save(userPermission);
        return ResponseEntity.created(new URI("/api/user-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-permissions : Updates an existing userPermission.
     *
     * @param userPermission the userPermission to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userPermission,
     * or with status 400 (Bad Request) if the userPermission is not valid,
     * or with status 500 (Internal Server Error) if the userPermission couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-permissions")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserPermission> updateUserPermission(@RequestBody UserPermission userPermission) throws URISyntaxException {
        log.debug("REST request to update UserPermission : {}", userPermission);
        if (userPermission.getId() == null) {
            return createUserPermission(userPermission);
        }
        UserPermission result = userPermissionRepository.save(userPermission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userPermission.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-permissions : get all the userPermissions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userPermissions in body
     */
    @GetMapping("/user-permissions")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public List<UserPermission> getAllUserPermissions() {
        List<UserPermission> userPermissions = userPermissionRepository.findAllWithEagerRelationships();
        Collections.sort(userPermissions);
        log.debug("REST request to get all UserPermissions");
        return userPermissions;
        }

    /**
     * GET  /user-permissions/:id : get the "id" userPermission.
     *
     * @param id the id of the userPermission to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userPermission, or with status 404 (Not Found)
     */
    @GetMapping("/user-permissions/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserPermission> getUserPermission(@PathVariable Long id) {
        log.debug("REST request to get UserPermission : {}", id);
        UserPermission userPermission = userPermissionRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userPermission));
    }

    /**
     * DELETE  /user-permissions/:id : delete the "id" userPermission.
     *
     * @param id the id of the userPermission to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-permissions/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUserPermission(@PathVariable Long id) {
        log.debug("REST request to delete UserPermission : {}", id);
        userPermissionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
