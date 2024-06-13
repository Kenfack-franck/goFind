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
import { ICarpool } from 'app/shared/model/carpool.model';
import { getEntities as getCarpools } from 'app/entities/carpool/carpool.reducer';
import { IPassenger } from 'app/shared/model/passenger.model';
import { getEntity, updateEntity, createEntity, reset } from './passenger.reducer';

export const PassengerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const utilisateurs = useAppSelector(state => state.utilisateur.entities);
  const carpools = useAppSelector(state => state.carpool.entities);
  const passengerEntity = useAppSelector(state => state.passenger.entity);
  const loading = useAppSelector(state => state.passenger.loading);
  const updating = useAppSelector(state => state.passenger.updating);
  const updateSuccess = useAppSelector(state => state.passenger.updateSuccess);

  const handleClose = () => {
    navigate('/passenger');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUtilisateurs({}));
    dispatch(getCarpools({}));
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
    values.joinDate = convertDateTimeToServer(values.joinDate);

    const entity = {
      ...passengerEntity,
      ...values,
      utilisateur: utilisateurs.find(it => it.id.toString() === values.utilisateur?.toString()),
      carpools: mapIdList(values.carpools),
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
          joinDate: displayDefaultDateTime(),
        }
      : {
          ...passengerEntity,
          joinDate: convertDateTimeFromServer(passengerEntity.joinDate),
          utilisateur: passengerEntity?.utilisateur?.id,
          carpools: passengerEntity?.carpools?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gofindApp.passenger.home.createOrEditLabel" data-cy="PassengerCreateUpdateHeading">
            <Translate contentKey="gofindApp.passenger.home.createOrEditLabel">Create or edit a Passenger</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="passenger-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('gofindApp.passenger.status')}
                id="passenger-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField
                label={translate('gofindApp.passenger.joinDate')}
                id="passenger-joinDate"
                name="joinDate"
                data-cy="joinDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="passenger-utilisateur"
                name="utilisateur"
                data-cy="utilisateur"
                label={translate('gofindApp.passenger.utilisateur')}
                type="select"
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
              <ValidatedField
                label={translate('gofindApp.passenger.carpools')}
                id="passenger-carpools"
                data-cy="carpools"
                type="select"
                multiple
                name="carpools"
              >
                <option value="" key="0" />
                {carpools
                  ? carpools.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/passenger" replace color="info">
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

export default PassengerUpdate;
