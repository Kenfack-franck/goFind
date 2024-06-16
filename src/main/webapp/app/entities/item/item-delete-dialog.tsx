import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './item.reducer';

export const ItemDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const itemEntity = useAppSelector(state => state.item.entity);
  const updateSuccess = useAppSelector(state => state.item.updateSuccess);

  const handleClose = () => {
    navigate('/item' + pageLocation.search);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(itemEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="itemDeleteDialogHeading">
        {/* <Translate contentKey="entity.delete.title">Confirm delete operation</Translate> */}
        Confirmation achat
      </ModalHeader>
      <ModalBody id="gofindApp.item.delete.question">
        {/* <Translate contentKey="gofindApp.item.delete.question" interpolate={{ id: itemEntity.id }}>
          Are you sure you want to delete this Item?
        </Translate> */}
        vous ete sur de vouloir acheter le produit {itemEntity.name}? si oui veuillez nous contacter au numero suivant 674727486.
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-item" data-cy="entityConfirmDeleteButton" color="info" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          {/* <Translate contentKey="entity.action.delete">Delete</Translate> */}
          valider
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default ItemDeleteDialog;
