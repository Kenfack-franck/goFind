import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { getEntities as getUtilisateurs } from 'app/entities/utilisateur/utilisateur.reducer';
import { IItem } from 'app/shared/model/item.model';
import { getEntity, updateEntity, createEntity, reset } from './item.reducer';

export const ItemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const utilisateurs = useAppSelector(state => state.utilisateur.entities);
  const itemEntity = useAppSelector(state => state.item.entity);
  const loading = useAppSelector(state => state.item.loading);
  const updating = useAppSelector(state => state.item.updating);
  const updateSuccess = useAppSelector(state => state.item.updateSuccess);

  const handleClose = () => {
    navigate('/item' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUtilisateurs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.creationDate = convertDateTimeToServer(values.creationDate);

    const entity = {
      ...itemEntity,
      ...values,
      owner: utilisateurs.find(it => it.id.toString() === values.owner?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          creationDate: displayDefaultDateTime(),
        }
      : {
          ...itemEntity,
          creationDate: convertDateTimeFromServer(itemEntity.creationDate),
          owner: itemEntity?.owner?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gofindApp.item.home.createOrEditLabel" data-cy="ItemCreateUpdateHeading">
            <Translate contentKey="gofindApp.item.home.createOrEditLabel">Create or edit a Item</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {/* {!isNew ? (
                      <ValidatedField
                        name="id"
                        required
                        readOnly
                        id="item-id"
                        label={translate('global.field.id')}
                        validate={{ required: true }}
                      />
                    ) : null} */}
              <ValidatedField
                label={translate('gofindApp.item.name')}
                id="item-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
                readOnly={!isNew}
              />
              <ValidatedField
                label={translate('gofindApp.item.description')}
                id="item-description"
                name="description"
                data-cy="description"
                type="text"
                readOnly={!isNew}
              />
              <ValidatedField
                label={translate('gofindApp.item.category')}
                id="item-category"
                name="category"
                data-cy="category"
                type="text"
                readOnly={!isNew}
              />
              <ValidatedField label={translate('gofindApp.item.status')} id="item-status" name="status" data-cy="status" type="select">
                <option value="a vendre">A vendre</option>
                <option value="voler">Voler</option>
                <option value="retrouver">Retrouver</option>
              </ValidatedField>
              <ValidatedField
                label={translate('gofindApp.item.creationDate')}
                id="item-creationDate"
                name="creationDate"
                data-cy="creationDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
                readOnly={!isNew}
              />
              <ValidatedField
                id="item-owner"
                name="owner"
                data-cy="owner"
                label={translate('gofindApp.item.owner')}
                type="select"
                readOnly={!isNew}
              >
                <option value="" key="0" />
                {utilisateurs
                  ? utilisateurs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/item" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ItemUpdate;
